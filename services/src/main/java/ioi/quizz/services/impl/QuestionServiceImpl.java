package ioi.quizz.services.impl;

import ioi.quizz.lib.requests.QuestionCreateRequest;
import ioi.quizz.persistence.*;
import ioi.quizz.services.QuestionService;
import ioi.quizz.services.RoomService;
import ioi.quizz.services.StudentService;
import ioi.quizz.services.ThemeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

@RequestScoped
public class QuestionServiceImpl implements QuestionService {
    
    @Inject
    private EntityManager em;
    
    @Inject
    private ThemeService themeService;
    
    @Inject
    private RoomService roomService;
    
    @Inject
    private StudentService studentService;
    
    @Override
    public void addQuestion(String deviceId, QuestionCreateRequest request) {
        try {
            ThemeEntity theme = themeService.getThemeEntity(request.getThemeId())
                .orElseThrow();
    
            RoomEntity room = roomService.getRoomEntity(request.getRoomId())
                .orElseThrow();
    
            StudentEntity student = studentService.getStudentEntityByDevice(deviceId)
                .orElseThrow();
            
            ThemeQuestionEntity question = new ThemeQuestionEntity();
            question.setTheme(theme);
            question.setContent(request.getQuestion());
            question.setRoom(room);
            question.setStudentId(student.getId());
            
            em.getTransaction().begin();
            em.persist(question);
            
            request.getAnswers().stream().map(answer -> {
                QuestionAnswerEntity answerEntity = new QuestionAnswerEntity();
                answerEntity.setQuestion(question);
                answerEntity.setContent(answer.getText());
                answerEntity.setCorrect(answer.isCorrect());
                return answerEntity;
            }).forEach(answer -> {
                em.persist(answer);
            });
            
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
