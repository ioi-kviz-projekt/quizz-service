package ioi.quizz.lib.requests;

import java.util.List;

public class QuestionCreateRequest {
    
    private String themeId;
    
    private String question;
    
    private List<QuestionAnswer> answers;
    
    private String roomId;
    
    public String getThemeId() {
        return themeId;
    }
    
    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public List<QuestionAnswer> getAnswers() {
        return answers;
    }
    
    public void setAnswers(List<QuestionAnswer> answers) {
        this.answers = answers;
    }
    
    public String getRoomId() {
        return roomId;
    }
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public static class QuestionAnswer {
        
        private String text;
        
        private boolean correct;
    
        public String getText() {
            return text;
        }
    
        public void setText(String text) {
            this.text = text;
        }
    
        public boolean isCorrect() {
            return correct;
        }
    
        public void setCorrect(boolean correct) {
            this.correct = correct;
        }
    }
}
