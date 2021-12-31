package ioi.quizz.runnables;


import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import ioi.quizz.lib.enums.QuizState;
import ioi.quizz.persistence.QuizInstanceEntity;
import ioi.quizz.workers.QuizWorker;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class QuizStateHandler implements Runnable {
    
    private static final Logger LOG = LogManager.getLogger(QuizStateHandler.class.getName());
    
    @Inject
    private QuizWorker quizWorker;
    
    @Override
    @ActivateRequestContext
    public void run() {
        List<QuizInstanceEntity> quizzes = quizWorker.getActiveQuizzes();
        quizzes.forEach(this::handleQuizState);
    }
    
    private void handleQuizState(QuizInstanceEntity quiz) {
        Date now = new Date();
        Date phaseEnd = quiz.getStateEndsAt();
        
        try {
            // if phase has expired, set new phase based on previous state
            if (phaseEnd != null && phaseEnd.before(now)) {
                LOG.info("Handling quiz {} state!", quiz.getId());
                if (quiz.getState().equals(QuizState.WAITING)) {
                    // if current state is waiting, check for finish
                    // condition, or set new question
                    quizWorker.handleNextQuestion(quiz);
                } else if (quiz.getState().equals(QuizState.QUESTION)) {
                    // if current state is question and has expired
                    // start waiting phase (unless finish condition is true)
                    boolean lastQuestion = quizWorker.finishIfLastQuestion(quiz);
                    if (lastQuestion) {
                        return;
                    }
                    quizWorker.startWaiting(quiz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // phase has not yet expired, ignore
    }
}

