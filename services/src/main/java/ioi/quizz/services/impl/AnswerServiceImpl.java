package ioi.quizz.services.impl;

import ioi.quizz.lib.UserAnswer;
import ioi.quizz.lib.responses.QuizSummary;
import ioi.quizz.persistence.*;
import ioi.quizz.services.AnswerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnswerServiceImpl implements AnswerService {
    
    /*@Inject
    private EntityManagerFactory emFactory;*/
    
    @Inject
    private EntityManager em;
    
    @Override
    public void saveUserAnswer(UserAnswer userAnswer) {
        // EntityManager em = emFactory.createEntityManager();
    
        QuestionAnswerEntity answer = em.find(QuestionAnswerEntity.class, userAnswer.getAnswerId());
        ThemeQuestionEntity question = em.find(ThemeQuestionEntity.class, userAnswer.getQuestionId());
        QuizInstanceEntity quiz = em.find(QuizInstanceEntity.class, userAnswer.getQuizId());
        String userId = userAnswer.getUserId();
        
        if (answer == null || question == null || quiz == null) {
            return;
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
        }
    }
    
    @Override
    public QuizSummary getUserSummary(String userId, String quizId) {
        // EntityManager em = emFactory.createEntityManager();
    
        TypedQuery<QuizQuestionEntity> query = em.createNamedQuery(QuizQuestionEntity.GET_QUIZ_QUESTIONS, QuizQuestionEntity.class);
        query.setParameter("quizId", quizId);
        List<QuizQuestionEntity> quizQuestions = query.getResultList();
        
        TypedQuery<UserAnswerEntity> userQuery = em.createNamedQuery(UserAnswerEntity.GET_USER_ANSWERS, UserAnswerEntity.class);
        userQuery.setParameter("quizId", quizId);
        userQuery.setParameter("userId", userId);
        Map<String, UserAnswerEntity> userAnswers = userQuery.getResultStream()
            .collect(Collectors.toMap(ua -> ua.getQuestion().getId(), ua -> ua));
        
        QuizSummary summary = new QuizSummary();
        summary.setTotal(quizQuestions.size());
        
        int correct = quizQuestions.stream()
            .map(question -> {
                if (userAnswers.containsKey(question.getId())) {
                    UserAnswerEntity userAnswer = userAnswers.get(question.getId());
                    if (userAnswer.getAnswer().isCorrect()) {
                        return 1;
                    }
                }
                return 0;
            }).reduce(0, Integer::sum);
        summary.setCorrect(correct);
        return summary;
    }
}