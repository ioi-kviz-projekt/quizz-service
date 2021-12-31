package ioi.quizz.workers;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import ioi.quizz.context.DiscoverySessionContext;
import ioi.quizz.lib.enums.SocketMessageType;
import ioi.quizz.lib.ws.SocketMessage;
import ioi.quizz.utils.TokenUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.websocket.Session;
import java.util.stream.Collectors;

@ApplicationScoped
public class DiscoveryWorker {
    
    private static final Logger LOG = LogManager.getLogger(DiscoveryWorker.class.getName());
    
    public void registerSocketSession(Session session) {
        session.setMaxIdleTimeout(0);
        LOG.info("Registered new socket session with id: " + session.getId());
        DiscoverySessionContext.openSession(session);
    }
    
    @ActivateRequestContext
    public void handleMessage(SocketMessage message, Session session) {
        if (message.getType().equals(SocketMessageType.PING.getType())) {
            LOG.info("Received PING from session with id: " + session.getId());
            SocketMessage pong = new SocketMessage();
            pong.setType("PONG");
            sendMessage(pong, session);
            return;
        }
        
        if (message.getType().equals(SocketMessageType.REGISTRATION.getType())) {
            LOG.info("Received REGISTRATION from session with id: " + session.getId());
            String deviceId = message.getAccessToken();
            session.getUserProperties().put("deviceId", deviceId);
            return;
        }
        
    }
    
    public void broadcast(SocketMessage message) {
        DiscoverySessionContext.getAllSessions().forEach(session -> {
            session.getAsyncRemote().sendObject(message);
        });
    }
    
    public void sendMessage(SocketMessage message, Session session) {
        session.getAsyncRemote().sendObject(message);
    }
    
    public void sendMessage(SocketMessage message, String deviceId) {
        var sessions = DiscoverySessionContext.getAllSessions().stream()
            .filter(session -> {
                var storedId = session.getUserProperties().get("deviceId");
                return storedId != null && storedId.equals(deviceId);
            })
            .collect(Collectors.toList());
        
        if (sessions.size() > 0) {
            sendMessage(message, sessions.get(0));
        } else {
            LOG.warn("No active session for user!");
        }
    }
}
