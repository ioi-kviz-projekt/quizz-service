package ioi.quizz.context;

import javax.websocket.Session;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SocketSessionContext {
    private static final ConcurrentHashMap<Session, Object> sessions;
    // private static Set<Session> sessions;
    
    static {
        // sessions = new HashSet<>();
        sessions = new ConcurrentHashMap<>();
    }
    
    public static void openSession(Session session) {
        sessions.put(session, 0);
    }
    
    public static void closeSession(Session session) {
        sessions.remove(session);
    }
    
    public static Set<Session> getAllSessions() {
        return new HashSet<>(sessions.keySet());
    }
    
}
