package ioi.quizz.services.impl;

import ioi.quizz.lib.UserAnswer;
import ioi.quizz.lib.responses.QuizSummary;
import ioi.quizz.persistence.*;
import ioi.quizz.services.AnswerService;
import ioi.quizz.services.QuizService;
import ioi.quizz.services.StudentService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestScoped
public class AnswerServiceImpl implements AnswerService {
    
    @Inject
    private EntityManager em;
    
    @Inject
    private StudentService studentService;
    
    @Inject
    private QuizService quizService;
    
    @Override
    public void saveUserAnswer(UserAnswer userAnswer, String deviceId) {
        QuizInstanceEntity quiz = quizService.getActiveQuizEntity(deviceId, userAnswer.getRoomId())
            .orElseThrow();
        QuestionAnswerEntity answer = getAnswerEntity(userAnswer.getAnswerId())
            .orElseThrow();
        ThemeQuestionEntity question = getQuestionEntity(userAnswer.getQuestionId())
            .orElseThrow();
        StudentEntity student = studentService.getStudentEntityByDevice(deviceId)
            .orElseThrow();
        
        UserAnswerEntity entity = new UserAnswerEntity();
        entity.setAnswer(answer);
        entity.setQuestion(question);
        entity.setQuiz(quiz);
        entity.setStudent(student);
        
        try {
            em.getTransaction().begin();
            em.persist(entity);
            // em.flush();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    
    @Override
    public Map<String, QuizSummary> getParticipantsSummaries(String quizId) {
        List<QuizQuestionEntity> quizQuestions = getQuizQuestions(quizId);
        int total = quizQuestions.size();
    
        TypedQuery<UserAnswerEntity> userQuery = em.createNamedQuery(UserAnswerEntity.GET_USER_ANSWERS, UserAnswerEntity.class);
        userQuery.setParameter("quizId", quizId);
        List<UserAnswerEntity> userAnswers = userQuery.getResultList();
        
        Map<String, QuizSummary> userAnswersResult = new HashMap<>();
        
        userAnswers.forEach(userAnswer -> {
            String userId = userAnswer.getStudent().getId();
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
    
    private Optional<QuestionAnswerEntity> getAnswerEntity(String answerId) {
        return Optional.ofNullable(em.find(QuestionAnswerEntity.class, answerId));
    }
    
    private Optional<ThemeQuestionEntity> getQuestionEntity(String questionId) {
        return Optional.ofNullable(em.find(ThemeQuestionEntity.class, questionId));
    }
}
