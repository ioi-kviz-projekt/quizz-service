package ioi.quizz.utils;

import ioi.quizz.lib.enums.SocketMessageType;
import ioi.quizz.lib.responses.QuestionResponse;
import ioi.quizz.lib.responses.QuizSummary;
import ioi.quizz.lib.responses.StateChangeResponse;
import ioi.quizz.lib.ws.SocketMessage;

import java.util.Date;

public class SocketUtils {
    
    public static SocketMessage waiting(Date endsAt) {
        SocketMessage message = new SocketMessage();
        message.setType(SocketMessageType.WAITING.getType());
        
        StateChangeResponse response = new StateChangeResponse();
        response.setEndsAt(endsAt);
        message.setClassName("StateChangeResponse");
        message.setPayload(getPayload(response));
        
        return message;
    }
    
    public static SocketMessage question(QuestionResponse response) {
        SocketMessage message = new SocketMessage();
        message.setType(SocketMessageType.QUESTION.getType());
        
        message.setClassName("QuestionResponse");
        message.setPayload(getPayload(response));
    
        return message;
    }
    
    public static SocketMessage start(Date endsAt) {
        SocketMessage message = new SocketMessage();
        message.setType(SocketMessageType.START.getType());
        message.setClassName("StateChangeResponse");
        StateChangeResponse resp = new StateChangeResponse();
        resp.setEndsAt(endsAt);
        message.setPayload(getPayload(resp));
        return message;
    }
    
    public static SocketMessage finish() {
        SocketMessage message = new SocketMessage();
        message.setType(SocketMessageType.FINISHED.getType());
        return message;
    }
    
    public static SocketMessage summary(QuizSummary summary) {
        SocketMessage message = new SocketMessage();
        message.setType(SocketMessageType.SUMMARY.getType());
        message.setPayload(getPayload(summary));
        return message;
    }
    
    public static SocketMessage pong() {
        SocketMessage pong = new SocketMessage();
        pong.setType(SocketMessageType.PONG.getType());
        return pong;
    }
    
    private static <T> String getPayload(T payload) {
        return SerializationUtils.serializePayload(payload).orElseThrow();
    }
    
}
