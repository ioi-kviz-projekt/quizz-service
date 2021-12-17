package ioi.quizz.lib;

public class UserAnswer extends BaseType {
    
    private String userId;
    
    private String questionId;
    
    private String answerId;
    
    private String quizId;
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getQuestionId() {
        return questionId;
    }
    
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    
    public String getAnswerId() {
        return answerId;
    }
    
    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
    
    public String getQuizId() {
        return quizId;
    }
    
    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }
}
