package ioi.quizz.lib;

public class UserAnswer extends BaseType {
    
    private String questionId;
    
    private String answerId;
    
    private String roomId;
    
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
    
    public String getRoomId() {
        return roomId;
    }
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
