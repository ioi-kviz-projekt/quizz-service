package ioi.quizz.lib.enums;

public enum SocketMessageType {
    PING("PING"),
    REGISTRATION("REGISTRATION"),
    WAITING("WAITING"),
    START("START"),
    QUESTION("QUESTION"),
    FINISHED("FINISHED"),
    ANSWER("ANSWER"),
    REQUEST_SUMMARY("REQUEST_SUMMARY"),
    SUMMARY("SUMMARY");
    
    private final String type;
    
    SocketMessageType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
}
