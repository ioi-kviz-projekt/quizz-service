package ioi.quizz.lib.enums;

public enum SocketMessageType {
    // outgoing
    PING("PING"),
    REGISTRATION("REGISTRATION"),
    ANSWER("ANSWER"),
    
    // incoming
    PONG("PONG"),
    WAITING("WAITING"),
    START("START"),
    QUESTION("QUESTION"),
    FINISHED("FINISHED"),
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
