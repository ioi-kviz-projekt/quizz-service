package ioi.quizz.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.mjamsek.rest.exceptions.RestException;
import com.mjamsek.rest.exceptions.UnauthorizedException;
import ioi.quizz.config.QuizzConfig;
import ioi.quizz.lib.ActiveQuizState;
import ioi.quizz.lib.QuizInstance;
import ioi.quizz.lib.enums.QuizState;
import ioi.quizz.lib.ws.SocketMessage;
import ioi.quizz.mappers.QuizMapper;
import ioi.quizz.persistence.QuizInstanceEntity;
import ioi.quizz.persistence.QuizQuestionEntity;
import ioi.quizz.persistence.RoomEntity;
import ioi.quizz.persistence.ThemeQuestionEntity;
import ioi.quizz.services.QuizService;
import ioi.quizz.services.RoomService;
import ioi.quizz.services.SocketService;
import ioi.quizz.utils.SocketUtils;
import ioi.quizz.utils.StringUtils;
import ioi.quizz.utils.TimeUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            
            questions.forEach(q -> {
                QuizQuestionEntity question = new QuizQuestionEntity();
                question.setQuiz(instance);
                question.setPosition(100);
                question.setQuestion(q);
                em.persist(question);
            });
            
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
    public void startQuiz(String id) {
        QuizInstanceEntity quiz = getQuizzEntity(id).orElseThrow();
        
        // update quiz status in database
        try {
            em.getTransaction().begin();
            quiz.setQuestionIndex(0);
            quiz.setState(QuizState.WAITING);
            Date stateEndsAt = TimeUtils.getSecondsAfterNow(quizzConfig.getLoadingDuration());
            quiz.setStateEndsAt(stateEndsAt);
            em.merge(quiz);
            em.flush();
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
        // TODO: finish
        QuizInstanceEntity quiz = getActiveInstanceInRoom(roomId)
            .orElseThrow(() -> new UnauthorizedException("No active quiz"));
        
        ActiveQuizState quizState = new ActiveQuizState(QuizMapper.fromEntity(quiz));
        if (quiz.getState().equals(QuizState.QUESTION)) {
        
        }
        return quizState;
    }
    
    @Override
    public Optional<QuizInstanceEntity> getActiveQuizEntity(String deviceId, String roomId) {
        return Optional.empty();
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
