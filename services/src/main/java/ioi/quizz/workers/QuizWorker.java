package ioi.quizz.workers;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import ioi.quizz.config.QuizzConfig;
import ioi.quizz.lib.QuestionAnswer;
import ioi.quizz.lib.enums.QuizState;
import ioi.quizz.lib.responses.QuestionResponse;
import ioi.quizz.lib.ws.SocketMessage;
import ioi.quizz.mappers.QuizMapper;
import ioi.quizz.persistence.QuizInstanceEntity;
import ioi.quizz.persistence.ThemeQuestionEntity;
import ioi.quizz.services.SocketService;
import ioi.quizz.utils.SocketUtils;
import ioi.quizz.utils.TimeUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class QuizWorker {
    
    private static final Logger LOG = LogManager.getLogger(QuizWorker.class.getName());
    
    @Inject
    private EntityManager em;
    
    @Inject
    private QuestionWorker questionWorker;
    
    @Inject
    private SocketService socketService;
    
    @Inject
    private QuizzConfig quizzConfig;
    
    public List<QuizInstanceEntity> getActiveQuizzes() {
        TypedQuery<QuizInstanceEntity> query = em.createNamedQuery(QuizInstanceEntity.GET_ACTIVES, QuizInstanceEntity.class);
        return query.getResultList();
    }
    
    public void finishQuiz(QuizInstanceEntity quiz) {
        LOG.info("Quiz ({}) is going from WAITING to FINISHED...", quiz.getId());
        try {
            em.getTransaction().begin();
            quiz.setState(QuizState.FINISHED);
            quiz.setStateEndsAt(null);
            quiz.setActive(false);
            // em.merge(quiz);
            // em.flush();
            em.getTransaction().commit();
    
            SocketMessage finishMessage = SocketUtils.finish();
            socketService.broadcast(finishMessage);
    
            // TODO: summary
            // sendQuizSummary(quiz.getId());
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    
    public boolean finishIfLastQuestion(QuizInstanceEntity quiz) {
        int remainingQuestions = questionWorker.countRemainingQuestions(quiz.getId());
        if (remainingQuestions == 0) {
            finishQuiz(quiz);
            return true;
        }
        return false;
    }
    
    /**
     * @param quiz quiz instance
     * @return true if no more questions - terminate execution flow
     */
    public boolean handleNextQuestion(QuizInstanceEntity quiz) {
        Optional<ThemeQuestionEntity> question = questionWorker.getNextQuestion(quiz.getId());
        if (question.isEmpty()) {
            finishQuiz(quiz);
            return true;
        }
        setNextQuestion(quiz, question.get());
        return false;
    }
    
    public void setNextQuestion(QuizInstanceEntity quiz, ThemeQuestionEntity question) {
        LOG.info("Quiz ({}) is going from WAITING to QUESTION...", quiz.getId());
        // set new state on quiz, increment question index
        try {
            em.getTransaction().begin();
            quiz.setQuestionIndex(quiz.getQuestionIndex() + 1);
            quiz.setState(QuizState.QUESTION);
            Date stateEndsAt = TimeUtils.getSecondsAfterNow(quizzConfig.getQuestionDuration());
            quiz.setStateEndsAt(stateEndsAt);
            // em.merge(quiz);
            // em.flush();
            em.getTransaction().commit();
    
            List<QuestionAnswer> answers = questionWorker.getQuestionAnswers(question.getId())
                .map(QuizMapper::fromEntity)
                .collect(Collectors.toList());
    
            QuestionResponse response = new QuestionResponse();
            response.setQuestion(QuizMapper.fromEntity(question));
            response.setAnswers(answers);
            response.setEndsAt(stateEndsAt);
    
            // send question via message
            SocketMessage questionMessage = SocketUtils.question(response);
            socketService.broadcast(questionMessage);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    
    public void startWaiting(QuizInstanceEntity quiz) {
        LOG.info("Quiz ({}) is going from QUESTION to WAITING...", quiz.getId());
        try {
            em.getTransaction().begin();
            quiz.setState(QuizState.WAITING);
            Date stateEndsAt = TimeUtils.getSecondsAfterNow(quizzConfig.getLoadingDuration());
            quiz.setStateEndsAt(stateEndsAt);
            // em.merge(quiz);
            // em.flush();
            em.getTransaction().commit();
    
            // update state via message
            SocketMessage waitingMessage = SocketUtils.waiting(stateEndsAt);
            socketService.broadcast(waitingMessage);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    
    /*public Map<String, QuizSummary> getQuizSummaries(String quizId) {
        List<QuizQuestionEntity> quizQuestions = getQuizQuestions(quizId);
        int total = quizQuestions.size();
        
        TypedQuery<UserAnswerEntity> userQuery = em.createNamedQuery(UserAnswerEntity.GET_USER_ANSWERS, UserAnswerEntity.class);
        userQuery.setParameter("quizId", quizId);
        List<UserAnswerEntity> userAnswers = userQuery.getResultList();
        
        Map<String, QuizSummary> userAnswersResult = new HashMap<>();
        
        userAnswers.forEach(userAnswer -> {
            String userId = userAnswer.getUserId();
            if (userAnswersResult.containsKey(userId)) {
                QuizSummary summary = userAnswersResult.get(userId);
                if (userAnswer.getAnswer().isCorrect()) {
                    summary.addCorrect(1);
                }
                userAnswersResult.put(userId, summary);
            } else {
                QuizSummary summary = QuizSummary.empty(total);
                if (userAnswer.getAnswer().isCorrect()) {
                    summary.addCorrect(1);
                }
                userAnswersResult.put(userId, summary);
            }
        });
        
        return userAnswersResult;
    }*/
    
    /*private List<QuizQuestionEntity> getQuizQuestions(String quizId) {
        TypedQuery<QuizQuestionEntity> query = em.createNamedQuery(QuizQuestionEntity.GET_QUIZ_QUESTIONS, QuizQuestionEntity.class);
        query.setParameter("quizId", quizId);
        return query.getResultList();
    }*/
    
}
