package ioi.quizz.persistence;

import javax.persistence.*;

@Entity
@Table(name = "question_answers")
@NamedQueries({
    @NamedQuery(name = QuestionAnswerEntity.GET_ANSWERS, query = "SELECT a FROM QuestionAnswerEntity a WHERE a.question.id = :questionId")
})
public class QuestionAnswerEntity extends BaseEntity {
    
    public static final String GET_ANSWERS = "QuestionAnswerEntity.getAnswers";
    
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "correct")
    private boolean correct;
    
    @ManyToOne
    @JoinColumn(name = "question_id")
    private ThemeQuestionEntity question;
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public boolean isCorrect() {
        return correct;
    }
    
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
    
    public ThemeQuestionEntity getQuestion() {
        return question;
    }
    
    public void setQuestion(ThemeQuestionEntity question) {
        this.question = question;
    }
}
