package ioi.quizz.runnables;


import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import ioi.quizz.config.QuizzConfig;
import ioi.quizz.context.SocketSessionContext;
import ioi.quizz.lib.QuestionAnswer;
import ioi.quizz.lib.enums.QuizState;
import ioi.quizz.lib.responses.QuestionResponse;
import ioi.quizz.lib.responses.QuizSummary;
import ioi.quizz.lib.ws.SocketMessage;
import ioi.quizz.mappers.QuizMapper;
import ioi.quizz.persistence.QuizInstanceEntity;
import ioi.quizz.persistence.ThemeQuestionEntity;
import ioi.quizz.services.AnswerService;
import ioi.quizz.services.SocketService;
import ioi.quizz.utils.SocketUtils;
import ioi.quizz.utils.TimeUtils;
import ioi.quizz.workers.QuestionWorker;
import ioi.quizz.workers.QuizWorker;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuizStateHandler implements Runnable {
    
    private static final Logger LOG = LogManager.getLogger(QuizStateHandler.class.getName());
    
    @Inject
    private EntityManager em;
    
    @Inject
    private QuizWorker quizWorker;
    
    @Inject
    private QuestionWorker questionWorker;
    
    @Inject
    private AnswerService answerService;
    
    @Inject
    private SocketService socketService;
    
    @Inject
    private QuizzConfig quizzConfig;
    
    @Override
    @ActivateRequestContext
    public void run() {
        List<QuizInstanceEntity> quizzes = quizWorker.getActiveQuizzes();
        quizzes.forEach(this::handleQuizState);
    }
    
    private void handleQuizState(QuizInstanceEntity quiz) {
        // EntityManager em = emFactory.createEntityManager();
        Date now = new Date();
        Date phaseEnd = quiz.getStateEndsAt();
        
        try {
            // if phase has expired, set new phase based on previous state
            if (phaseEnd != null && phaseEnd.before(now)) {
                if (quiz.getState().equals(QuizState.WAITING)) {
                    // get next question
                    Optional<ThemeQuestionEntity> question = questionWorker.getNextQuestion(quiz.getId());
                    if (question.isEmpty()) {
                        finishQuiz(quiz, em);
                        return;
                    }
                    setNextQuestion(quiz, question.get(), em);
                } else if (quiz.getState().equals(QuizState.QUESTION)) {
                    boolean lastQuestion = handleIfLastQuestion(quiz, em);
                    if (lastQuestion) {
                        return;
                    }
                    startWaiting(quiz, em);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        // phase has not yet expired, ignore quiz
    }
    
    private boolean handleIfLastQuestion(QuizInstanceEntity quiz, EntityManager em) {
        int remainingQuestions = questionWorker.countRemainingQuestions(quiz.getId());
        if (remainingQuestions == 0) {
            finishQuiz(quiz, em);
            return true;
        }
        return false;
    }
    
    // @ActivateRequestContext
    private void finishQuiz(QuizInstanceEntity quiz, EntityManager em) {
        LOG.info("Quiz ({}) is going from WAITING to FINISHED...", quiz.getId());
        em.getTransaction().begin();
        quiz.setState(QuizState.FINISHED);
        quiz.setStateEndsAt(null);
        quiz.setActive(false);
        em.merge(quiz);
        em.flush();
        em.getTransaction().commit();
    
        SocketMessage finishMessage = SocketUtils.finish();
        socketService.broadcast(finishMessage);
        
        sendQuizSummary(quiz.getId());
    }
    
    private void sendQuizSummary(String quizId) {
        Map<String, QuizSummary> summaries = answerService.getParticipantsSummaries(quizId);
    
        SocketSessionContext.getAllSessions().forEach(session -> {
            if (session.getUserProperties().containsKey("userId")) {
                String userId = (String) session.getUserProperties().get("userId");
                if (summaries.containsKey(userId)) {
                    SocketMessage summaryMessage = SocketUtils.summary(summaries.get(userId));
                    socketService.sendMessage(summaryMessage, session);
                }
            }
        });
    }
    
    private void setNextQuestion(QuizInstanceEntity quiz, ThemeQuestionEntity question, EntityManager em) {
        LOG.info("Quiz ({}) is going from WAITING to QUESTION...", quiz.getId());
        // set new state on quiz, increment question index
        em.getTransaction().begin();
        quiz.setQuestionIndex(quiz.getQuestionIndex() + 1);
        quiz.setState(QuizState.QUESTION);
        Date stateEndsAt = TimeUtils.getSecondsAfterNow(quizzConfig.getQuestionDuration());
        quiz.setStateEndsAt(stateEndsAt);
        em.merge(quiz);
        em.flush();
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
    }
    
    private void startWaiting(QuizInstanceEntity quiz, EntityManager em) {
        LOG.info("Quiz ({}) is going from QUESTION to WAITING...", quiz.getId());
        em.getTransaction().begin();
        quiz.setState(QuizState.WAITING);
        Date stateEndsAt = TimeUtils.getSecondsAfterNow(quizzConfig.getLoadingDuration());
        quiz.setStateEndsAt(stateEndsAt);
        em.merge(quiz);
        em.flush();
        em.getTransaction().commit();
    
        // update state via message
        SocketMessage waitingMessage = SocketUtils.waiting(stateEndsAt);
        socketService.broadcast(waitingMessage);
    }
    
}

