package ioi.quizz.workers;

import ioi.quizz.persistence.QuestionAnswerEntity;
import ioi.quizz.persistence.QuizQuestionEntity;
import ioi.quizz.persistence.ThemeQuestionEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.Optional;
import java.util.stream.Stream;

@RequestScoped
public class QuestionWorker {
    
    @Inject
    private EntityManager em;
    
    public Optional<ThemeQuestionEntity> getNextQuestion(String quizId) {
        // EntityManager em = emFactory.createEntityManager();
        TypedQuery<QuizQuestionEntity> query = em.createNamedQuery(QuizQuestionEntity.GET_NEXT_QUESTION, QuizQuestionEntity.class);
        query.setParameter("quizId", quizId);
        query.setMaxResults(1);
        
        try {
            ThemeQuestionEntity result = query.getSingleResult().getQuestion();
            return Optional.of(result);
        } catch (NoResultException | NullPointerException e) {
            return Optional.empty();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
    public int countRemainingQuestions(String quizId) {
        // EntityManager em = emFactory.createEntityManager();
        TypedQuery<Long> query = em.createNamedQuery(QuizQuestionEntity.COUNT_REMAINING_QUESTIONS, Long.class);
        query.setParameter("quizId", quizId);
        
        try {
            long result = query.getSingleResult();
          
            return Math.toIntExact(result);
        } catch (NoResultException e) {
        
            return 0;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public Stream<QuestionAnswerEntity> getQuestionAnswers(String questionId) {
        // EntityManager em = emFactory.createEntityManager();
        TypedQuery<QuestionAnswerEntity> query = em.createNamedQuery(QuestionAnswerEntity.GET_ANSWERS, QuestionAnswerEntity.class);
        query.setParameter("questionId", questionId);
        
        try {
            Stream<QuestionAnswerEntity> result = query.getResultStream();
            return result;
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }
}
