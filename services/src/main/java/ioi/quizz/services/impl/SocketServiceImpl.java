package ioi.quizz.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import ioi.quizz.context.SocketSessionContext;
import ioi.quizz.lib.UserAnswer;
import ioi.quizz.lib.enums.SocketMessageType;
import ioi.quizz.lib.responses.QuizSummary;
import ioi.quizz.lib.ws.SocketMessage;
import ioi.quizz.services.AnswerService;
import ioi.quizz.services.SocketService;
import ioi.quizz.utils.DeserializationUtils;
import ioi.quizz.utils.SocketUtils;
import ioi.quizz.utils.TokenUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class SocketServiceImpl implements SocketService {
    
    private static final Logger LOG = LogManager.getLogger(SocketServiceImpl.class.getName());
    
    @Inject
    private AnswerService answerService;
    
    @Override
    public void registerSocketSession(Session session) {
        session.setMaxIdleTimeout(0);
        LOG.info("Registered new socket session with id: " + session.getId());
        SocketSessionContext.openSession(session);
    }
    
    @Override
    public void handleMessage(SocketMessage message, Session session) {
        if (message.getType().equals(SocketMessageType.PING.getType())) {
            LOG.info("Received PING from session with id: " + session.getId());
            SocketMessage pong = new SocketMessage();
            pong.setType("PONG");
            sendMessage(pong, session);
            return;
        }
        
        if (message.getType().equals(SocketMessageType.ANSWER.getType()) && message.getAccessToken() != null) {
            LOG.info("Received ANSWER from session with id: " + session.getId());
            String userId = TokenUtils.getUserId(message.getAccessToken());
            UserAnswer payload = DeserializationUtils.deserializePayload(message.getPayload(), UserAnswer.class)
                .orElseThrow();
            payload.setUserId(userId);
            CompletableFuture.runAsync(() -> {
                answerService.saveUserAnswer(payload);
            });
            return;
        }
        
        if (message.getType().equals(SocketMessageType.REQUEST_SUMMARY.getType()) && message.getAccessToken() != null) {
            LOG.info("Received REQUEST_SUMMARY from session with id: " + session.getId());
            String userId = TokenUtils.getUserId(message.getAccessToken());
            QuizSummary summary = answerService.getUserSummary(userId, message.getPayload());
            SocketMessage summaryMessage = SocketUtils.summary(summary);
            sendMessage(summaryMessage, session);
            return;
        }
    }
    
    @Override
    public void broadcast(SocketMessage message) {
        SocketSessionContext.getAllSessions().forEach(session -> {
            session.getAsyncRemote().sendObject(message);
        });
    }
    
    @Override
    public void sendMessage(SocketMessage message, Session session) {
        session.getAsyncRemote().sendObject(message);
    }
}
