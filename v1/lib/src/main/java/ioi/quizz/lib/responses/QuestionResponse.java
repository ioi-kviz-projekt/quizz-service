package ioi.quizz.lib.responses;

import ioi.quizz.lib.QuestionAnswer;
import ioi.quizz.lib.ThemeQuestion;

import java.util.Date;
import java.util.List;

public class QuestionResponse {
    
    private Date endsAt;
    
    private ThemeQuestion question;
    
    private List<QuestionAnswer> answers;
    
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
