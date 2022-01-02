package ioi.quizz.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.mjamsek.rest.exceptions.NotFoundException;
import com.mjamsek.rest.exceptions.RestException;
import ioi.quizz.config.QuizzConfig;
import ioi.quizz.lib.ActiveQuizState;
import ioi.quizz.lib.QuestionAnswer;
import ioi.quizz.lib.QuizInstance;
import ioi.quizz.lib.enums.QuizState;
import ioi.quizz.lib.responses.QuizSummary;
import ioi.quizz.lib.ws.SocketMessage;
import ioi.quizz.mappers.QuizMapper;
import ioi.quizz.persistence.*;
import ioi.quizz.services.QuizService;
import ioi.quizz.services.RoomService;
import ioi.quizz.services.SocketService;
import ioi.quizz.services.StudentService;
import ioi.quizz.utils.SocketUtils;
import ioi.quizz.utils.StringUtils;
import ioi.quizz.utils.TimeUtils;
import ioi.quizz.workers.QuestionWorker;
import ioi.quizz.workers.QuizWorker;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class QuizServiceImpl implements QuizService {
    
    private static final Logger LOG = LogManager.getLogger(QuizServiceImpl.class.getName());
    
    @Inject
    private EntityManager em;
    
    @Inject
    private SocketService socketService;
    
    @Inject
    private RoomService roomService;
    
    @Inject
    private StudentService studentService;
    
    @Inject
    private QuizWorker quizWorker;
    
    @Inject
    private QuestionWorker questionWorker;
    
    @Inject
    private QuizzConfig quizzConfig;
    
    @Override
    public QuizInstance createInstance(String roomId) {
        var currentQuiz = getActiveInstanceInRoom(roomId);
        if (currentQuiz.isPresent()) {
            LOG.warn("Quiz cannot be created! There is already one active quiz in this room!");
            throw new RestException("Already active quiz!");
        }
    
        RoomEntity room = roomService.getRoomEntity(roomId)
            .orElseThrow();
        
        QuizInstanceEntity instance = new QuizInstanceEntity();
        instance.setActive(true);
        instance.setPasskey(StringUtils.randomString(8));
        instance.setQuestionIndex(0);
        instance.setRoom(room);
        instance.setState(QuizState.ACTIVE);
        
        try {
            em.getTransaction().begin();
            em.persist(instance);
            em.flush();
            
            TypedQuery<ThemeQuestionEntity> query = em.createNamedQuery(ThemeQuestionEntity.GET_RAND_QS, ThemeQuestionEntity.class);
            List<ThemeQuestionEntity> questions = query.getResultList();
            
            for (int i = 0; i < questions.size(); i++) {
                ThemeQuestionEntity q = questions.get(i);
                QuizQuestionEntity question = new QuizQuestionEntity();
                question.setQuiz(instance);
                question.setPosition(i + 1);
                question.setQuestion(q);
                em.persist(question);
            }
            
            em.getTransaction().commit();
            return QuizMapper.fromEntity(instance);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Optional<QuizInstanceEntity> getQuizzEntity(String id) {
        return Optional.ofNullable(em.find(QuizInstanceEntity.class, id));
    }
    
    @Override
    public QuizSummary getQuizSummary(String deviceId, String quizId) {
        StudentEntity student = studentService.getStudentEntityByDevice(deviceId)
            .orElseThrow();
        
        List<QuizQuestionEntity> quizQuestions = getQuizQuestions(quizId);
        QuizSummary summary = QuizSummary.empty(quizQuestions.size());
    
        TypedQuery<UserAnswerEntity> userQuery = em.createNamedQuery(UserAnswerEntity.GET_USER_ANSWERS, UserAnswerEntity.class);
        userQuery.setParameter("quizId", quizId);
        userQuery.setParameter("studentId", student.getId());
        List<UserAnswerEntity> userAnswers = userQuery.getResultList();
    
        userAnswers.forEach(userAnswer -> {
            if (userAnswer.getAnswer().isCorrect()) {
                summary.addCorrect(1);
            }
        });
        
        return summary;
    }
    
    private List<QuizQuestionEntity> getQuizQuestions(String quizId) {
        TypedQuery<QuizQuestionEntity> query = em.createNamedQuery(QuizQuestionEntity.GET_QUIZ_QUESTIONS, QuizQuestionEntity.class);
        query.setParameter("quizId", quizId);
        return query.getResultList();
    }
    
    @Override
    public void startQuiz(String id) {
        QuizInstanceEntity quiz = getQuizzEntity(id).orElseThrow();
        
        // update quiz status in database
        try {
            em.getTransaction().begin();
            quiz.setQuestionIndex(0);
            quiz.setState(QuizState.WAITING);
            quiz.setActive(true);
            Date stateEndsAt = TimeUtils.getSecondsAfterNow(quizzConfig.getLoadingDuration());
            quiz.setStateEndsAt(stateEndsAt);
            em.getTransaction().commit();
            
            // send broadcast to clients, that quiz has started
            SocketMessage startMessage = SocketUtils.start(stateEndsAt);
            socketService.broadcast(startMessage);
            
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public ActiveQuizState getActiveQuizState(String deviceId, String roomId) {
        QuizInstanceEntity quiz = getActiveInstanceInRoom(roomId)
            .orElseThrow(() -> new NotFoundException("No active quiz"));
        
        ActiveQuizState quizState = new ActiveQuizState(QuizMapper.fromEntity(quiz));
        
        if (quiz.getState().equals(QuizState.QUESTION)) {
            quizState.setEndsAt(quiz.getStateEndsAt());
            
            QuizQuestionEntity currentQuestion = quizWorker.getCurrentQuestion(quiz.getId())
                .orElseThrow();
            quizState.setQuestion(QuizMapper.fromEntity(currentQuestion.getQuestion()));
            
            List<QuestionAnswer> answers = questionWorker.getQuestionAnswers(currentQuestion.getQuestion().getId())
                .map(QuizMapper::fromEntity)
                .collect(Collectors.toList());
            quizState.setAnswers(answers);
        }
        
        if (quiz.getState().equals(QuizState.WAITING)) {
            quizState.setEndsAt(quiz.getStateEndsAt());
        }
        
        return quizState;
    }
    
    @Override
    public Optional<QuizInstanceEntity> getActiveQuizEntity(String deviceId, String roomId) {
        return getActiveInstanceInRoom(roomId);
    }
    
    private Optional<QuizInstanceEntity> getActiveInstanceInRoom(String roomId) {
        TypedQuery<QuizInstanceEntity> query = em.createNamedQuery(QuizInstanceEntity.GET_ACTIVE_IN_ROOM, QuizInstanceEntity.class);
        query.setParameter("roomId", roomId);
        
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
}
