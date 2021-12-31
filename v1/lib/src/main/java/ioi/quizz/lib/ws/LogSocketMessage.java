package ioi.quizz.lib.ws;

public class LogSocketMessage {
    
    private String severity;
    
    private String message;
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
