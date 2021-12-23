package ioi.quizz.api.websocket;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import ioi.quizz.context.SocketSessionContext;
import ioi.quizz.lib.ws.SocketMessage;
import ioi.quizz.mappers.SocketMessageDecoder;
import ioi.quizz.mappers.SocketMessageEncoder;
import ioi.quizz.services.SocketService;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.TimeoutException;


@ServerEndpoint(value = "/ws/quiz", encoders = SocketMessageEncoder.class, decoders = SocketMessageDecoder.class)
public class QuizWebSocket {
    
    @Inject
    private SocketService socketService;
    
    private static final Logger LOG = LogManager.getLogger(QuizWebSocket.class.getName());
    
    @OnMessage
    public void onMessage(SocketMessage message, Session session) {
        if (message != null) {
            socketService.handleMessage(message, session);
        }
    }
    
    @OnOpen
    public void onOpen(Session session) {
        LOG.debug("New socket connection with id {}", session.getId());
        socketService.registerSocketSession(session);
    }
    
    @OnClose
    public void onClose(Session session) {
        LOG.debug("Closing session with id {}", session.getId());
        SocketSessionContext.closeSession(session);
    }
    
    @OnError
    public void onError(Throwable throwable, Session session) {
        LOG.error("Session id: {}, error: {}", session.getId(), throwable.getMessage());
        throwable.printStackTrace();
        
        if (throwable.getCause() instanceof TimeoutException) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.SERVICE_RESTART, "Timeout"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
