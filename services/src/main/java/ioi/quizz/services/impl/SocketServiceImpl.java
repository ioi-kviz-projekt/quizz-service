package ioi.quizz.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import ioi.quizz.context.SocketSessionContext;
import ioi.quizz.lib.ws.SocketMessage;
import ioi.quizz.services.SocketService;

import javax.enterprise.context.RequestScoped;
import javax.websocket.Session;
import java.util.Set;
import java.util.stream.Collectors;

@RequestScoped
public class SocketServiceImpl implements SocketService {
    
    private static final Logger LOG = LogManager.getLogger(SocketServiceImpl.class.getName());
    
    @Override
    public void broadcast(SocketMessage message) {
        broadcast(message, SocketSessionContext.getAllSessions());
    }
    
    @Override
    public void broadcast(SocketMessage message, Set<Session> sessions) {
        sessions.forEach(session -> {
            session.getAsyncRemote().sendObject(message);
        });
    }
    
    @Override
    public void sendMessage(SocketMessage message, Session session) {
        session.getAsyncRemote().sendObject(message);
    }
    
    @Override
    public void sendMessage(SocketMessage message, String deviceId) {
        var sessions = SocketSessionContext.getAllSessions().stream()
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
