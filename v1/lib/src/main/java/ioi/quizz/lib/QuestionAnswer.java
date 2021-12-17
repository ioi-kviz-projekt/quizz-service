package ioi.quizz.lib;

public class QuestionAnswer extends BaseType {
    
    private String htmlContent;
    
    private String textContent;
    
    private Boolean correct;
    
    private String questionId;
    
    public String getHtmlContent() {
        return htmlContent;
    }
    
    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
    
    public String getTextContent() {
        return textContent;
    }
    
    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
    
    public Boolean getCorrect() {
        return correct;
    }
    
    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
    
    public String getQuestionId() {
        return questionId;
    }
    
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
