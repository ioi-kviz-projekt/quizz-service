package ioi.quizz.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import ioi.quizz.config.QuizzConfig;
import ioi.quizz.lib.QuizInstance;
import ioi.quizz.lib.enums.QuizState;
import ioi.quizz.lib.requests.QuizPasskey;
import ioi.quizz.lib.responses.QuizSummary;
import ioi.quizz.lib.ws.SocketMessage;
import ioi.quizz.mappers.QuizMapper;
import ioi.quizz.persistence.*;
import ioi.quizz.services.QuizService;
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
import javax.ws.rs.NotFoundException;
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
    private QuizzConfig quizzConfig;
    
    @Override
    public QuizInstance createInstance() {
        QuizInstanceEntity instance = new QuizInstanceEntity();
        instance.setActive(false);
        instance.setPasskey(StringUtils.randomString(8));
        instance.setQuestionIndex(0);
        
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
    public QuizInstance getQuizByPasskey(QuizPasskey passkey) {
        TypedQuery<QuizInstanceEntity> query = em.createNamedQuery(QuizInstanceEntity.GET_BY_PASSKEY, QuizInstanceEntity.class);
        query.setParameter("passkey", passkey.getPasskey());
        
        try {
            QuizInstanceEntity entity = query.getSingleResult();
            return QuizMapper.fromEntity(entity);
        } catch (NoResultException e) {
            throw new NotFoundException();
        }
    }
    
    @Override
    public Optional<QuizInstanceEntity> getQuizzEntity(String id) {
        return Optional.ofNullable(em.find(QuizInstanceEntity.class, id));
    }
    
    @Override
    public QuizSummary getQuizzSummary(String userId, String quizId) {
        /*QuestionAnswerEntity answer = em.find(QuestionAnswerEntity.class, userAnswer.getAnswerId());
        ThemeQuestionEntity question = em.find(ThemeQuestionEntity.class, userAnswer.getQuestionId());
        QuizInstanceEntity quiz = em.find(QuizInstanceEntity.class, userAnswer.getQuizId());
        String userId = userAnswer.getUserId();
    
        if (answer == null || question == null || quiz == null) {
            throw new RuntimeException();
        }
    
        UserAnswerEntity entity = new UserAnswerEntity();
        entity.setAnswer(answer);
        entity.setQuestion(question);
        entity.setQuiz(quiz);
        entity.setUserId(userId);
    
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.flush();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }*/
        return null;
    }
    
    @Override
    public void startQuiz(String id) {
        QuizInstanceEntity quiz = getQuizzEntity(id).orElseThrow();
        
        // update quiz status in database
        try {
            em.getTransaction().begin();
            quiz.setActive(true);
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
    
}
