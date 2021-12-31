package ioi.quizz.api.websocket;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import ioi.quizz.context.DiscoverySessionContext;
import ioi.quizz.lib.ws.SocketMessage;
import ioi.quizz.mappers.SocketMessageDecoder;
import ioi.quizz.mappers.SocketMessageEncoder;
import ioi.quizz.workers.DiscoveryWorker;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@ServerEndpoint(value = "/ws/discovery", encoders = SocketMessageEncoder.class, decoders = SocketMessageDecoder.class)
public class DiscoveryWebSocket {
    
    private static final Logger LOG = LogManager.getLogger(DiscoveryWebSocket.class.getName());
    
    @Inject
    private DiscoveryWorker discoveryWorker;
    
    @OnMessage
    public void onMessage(SocketMessage message, Session session) {
        if (message != null) {
            discoveryWorker.handleMessage(message, session);
        }
    }
    
    @OnOpen
    public void onOpen(Session session) {
        LOG.debug("New socket connection with id {}", session.getId());
        discoveryWorker.registerSocketSession(session);
    }
    
    @OnClose
    public void onClose(Session session) {
        LOG.debug("Closing session with id {}", session.getId());
        DiscoverySessionContext.closeSession(session);
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
