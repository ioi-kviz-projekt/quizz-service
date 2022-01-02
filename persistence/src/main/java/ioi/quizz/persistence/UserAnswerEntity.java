package ioi.quizz.persistence;

import javax.persistence.*;

@Entity
@Table(name = "user_answers")
@NamedQueries({
    @NamedQuery(name = UserAnswerEntity.GET_USER_ANSWERS, query = "SELECT ua FROM UserAnswerEntity ua WHERE ua.quiz.id = :quizId AND ua.student.id = :studentId")
})
public class UserAnswerEntity extends BaseEntity {
    
    public static final String GET_USER_ANSWERS = "UserAnswerEntity.getUserAnswers";
    
    @ManyToOne
    @JoinColumn(name = "question_id")
    private ThemeQuestionEntity question;
    
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private QuestionAnswerEntity answer;
    
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QuizInstanceEntity quiz;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
    
    public StudentEntity getStudent() {
        return student;
    }
    
    public void setStudent(StudentEntity student) {
        this.student = student;
    }
    
    public ThemeQuestionEntity getQuestion() {
        return question;
    }
    
    public void setQuestion(ThemeQuestionEntity question) {
        this.question = question;
    }
    
    public QuestionAnswerEntity getAnswer() {
        return answer;
    }
    
    public void setAnswer(QuestionAnswerEntity answer) {
        this.answer = answer;
    }
    
    public QuizInstanceEntity getQuiz() {
        return quiz;
    }
    
    public void setQuiz(QuizInstanceEntity quiz) {
        this.quiz = quiz;
    }
}
