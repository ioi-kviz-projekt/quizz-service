package ioi.quizz.api.websocket;

import ioi.quizz.lib.ws.LogSocketMessage;
import ioi.quizz.mappers.LogSocketMessageDecoder;
import ioi.quizz.mappers.LogSocketMessageEncoder;
import ioi.quizz.workers.LogWorker;

import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws/logs", encoders = {LogSocketMessageEncoder.class}, decoders = {LogSocketMessageDecoder.class})
public class LogWebSocket {
    
    @Inject
    private LogWorker logWorker;
    
    @OnMessage
    public void onMessage(LogSocketMessage message, Session session) {
        if (message != null) {
            logWorker.handleLogSocketMessage(message);
        }
    }
}
