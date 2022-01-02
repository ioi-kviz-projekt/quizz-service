package ioi.quizz.workers;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import ioi.quizz.context.SocketSessionContext;
import ioi.quizz.lib.UserAnswer;
import ioi.quizz.lib.enums.SocketMessageType;
import ioi.quizz.lib.ws.SocketMessage;
import ioi.quizz.services.AnswerService;
import ioi.quizz.services.SocketService;
import ioi.quizz.utils.DeserializationUtils;
import ioi.quizz.utils.SocketUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.Session;
import java.io.IOException;

@ApplicationScoped
public class WebsocketWorker {
    
    @Inject
    private SocketService socketService;
    
    @Inject
    private AnswerService answerService;
    
    private static final Logger LOG = LogManager.getLogger(WebsocketWorker.class.getName());
    
    private boolean logPing;
    
    @PostConstruct
    private void init() {
        logPing = ConfigurationUtil.getInstance().getBoolean("quizz.pings.enabled").orElse(false);
    }
    
    @ActivateRequestContext
    public void handleMessage(SocketMessage message, Session session) {
        
        if (message.getType().equals(SocketMessageType.PING.getType())) {
            handlePingRequest(session);
            return;
        }
        
        if (message.getType().equals(SocketMessageType.REGISTRATION.getType())) {
            handleRegistration(message.getAccessToken(), session);
            return;
        }
        
        if (message.getType().equals(SocketMessageType.ANSWER.getType()) && message.getAccessToken() != null) {
            handleAnswer(message, session);
        }
    }
    
    public void openSession(Session session) {
        session.setMaxIdleTimeout(0);
        LOG.info("Registered new socket session with id: " + session.getId());
        SocketSessionContext.openSession(session);
    }
    
    public void closeSession(Session session) {
        SocketSessionContext.closeSession(session);
    }
    
    private void handlePingRequest(Session session) {
        if (logPing) {
            LOG.info("Received PING from session with id: " + session.getId());
        }
        SocketMessage pong = SocketUtils.pong();
        socketService.sendMessage(pong, session);
    }
    
    private void handleRegistration(String deviceId, Session session) {
        LOG.info("Received REGISTRATION from session with id: " + session.getId());
        
        // Close all sessions with given deviceId
        SocketSessionContext.getAllSessions().stream()
            .filter(s -> {
                if (s == session) {
                    return false;
                }
                if (s.getUserProperties().containsKey("deviceId")) {
                    return s.getUserProperties().get("deviceId").equals(deviceId);
                }
                return false;
            })
            .forEach(s -> {
                try {
                    if (!session.getId().equals(s.getId())) {
                        s.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Only one active connection per device allowed!"));
                    }
                } catch (IOException ignored) {
                
                }
            });
        
        // Set deviceId for current session.
        session.getUserProperties().put("deviceId", deviceId);
    }
    
    private void handleAnswer(SocketMessage message, Session session) {
        LOG.info("Received ANSWER from session with id: " + session.getId());
        String deviceId = message.getAccessToken();
        UserAnswer payload = DeserializationUtils.deserializePayload(message.getPayload(), UserAnswer.class)
            .orElseThrow();
        answerService.saveUserAnswer(payload, deviceId);
    }
}
