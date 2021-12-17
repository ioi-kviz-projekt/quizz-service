package ioi.quizz.services;

import ioi.quizz.lib.ws.SocketMessage;

import javax.websocket.Session;

public interface SocketService {
    
    void registerSocketSession(Session session);
    
    void handleMessage(SocketMessage message, Session session);
    
    void broadcast(SocketMessage message);
    
    void sendMessage(SocketMessage message, Session session);
    
}
