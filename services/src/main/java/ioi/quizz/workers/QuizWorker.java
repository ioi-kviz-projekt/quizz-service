package ioi.quizz.workers;

import ioi.quizz.lib.responses.QuizSummary;
import ioi.quizz.persistence.QuizInstanceEntity;
import ioi.quizz.persistence.QuizQuestionEntity;
import ioi.quizz.persistence.UserAnswerEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScoped
public class QuizWorker {
    
    @Inject
    private EntityManager em;
    
    public List<QuizInstanceEntity> getActiveQuizzes() {
        // EntityManager em = emFactory.createEntityManager();
        TypedQuery<QuizInstanceEntity> query = em.createNamedQuery(QuizInstanceEntity.GET_ACTIVES, QuizInstanceEntity.class);
        List<QuizInstanceEntity> result = query.getResultList();
        return result;
    }
    
    public Map<String, QuizSummary> getQuizSummaries(String quizId) {
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
    }
    
    private List<QuizQuestionEntity> getQuizQuestions(String quizId) {
        TypedQuery<QuizQuestionEntity> query = em.createNamedQuery(QuizQuestionEntity.GET_QUIZ_QUESTIONS, QuizQuestionEntity.class);
        query.setParameter("quizId", quizId);
        return query.getResultList();
    }
    
}
