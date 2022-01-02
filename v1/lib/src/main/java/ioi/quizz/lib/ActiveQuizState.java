package ioi.quizz.lib;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActiveQuizState extends QuizInstance {
    
    private ThemeQuestion question;
    
    private List<QuestionAnswer> answers;
    
    private Date endsAt;
    
    public ActiveQuizState() {
    
    }
    
    public ActiveQuizState(QuizInstance quiz) {
        this.setId(quiz.getId());
        this.setCreatedAt(quiz.getCreatedAt());
        this.setUpdatedAt(quiz.getUpdatedAt());
        
        this.setState(quiz.getState());
        this.setStateEndsAt(quiz.getStateEndsAt());
        this.setActive(quiz.isActive());
    }
    
    public ThemeQuestion getQuestion() {
        return question;
    }
    
    public void setQuestion(ThemeQuestion question) {
        this.question = question;
    }
    
    public List<QuestionAnswer> getAnswers() {
        return answers;
    }
    
    public void setAnswers(List<QuestionAnswer> answers) {
        this.answers = answers;
    }
    
    public Date getEndsAt() {
        return endsAt;
    }
    
    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }
}
