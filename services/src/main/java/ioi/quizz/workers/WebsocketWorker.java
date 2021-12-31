package ioi.quizz.workers;

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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.websocket.Session;

@ApplicationScoped
public class WebsocketWorker {
    
    @Inject
    private SocketService socketService;
    
    @Inject
    private AnswerService answerService;
    
    private static final Logger LOG = LogManager.getLogger(WebsocketWorker.class.getName());
    
    @ActivateRequestContext
    public void handleMessage(SocketMessage message, Session session) {
        
        if (message.getType().equals(SocketMessageType.PING.getType())) {
            // LOG.info("Received PING from session with id: " + session.getId());
            SocketMessage pong = SocketUtils.pong();
            socketService.sendMessage(pong, session);
            return;
        }
    
        if (message.getType().equals(SocketMessageType.REGISTRATION.getType())) {
            LOG.info("Received REGISTRATION from session with id: " + session.getId());
            String deviceId = message.getAccessToken();
            session.getUserProperties().put("deviceId", deviceId);
            return;
        }
    
        if (message.getType().equals(SocketMessageType.ANSWER.getType()) && message.getAccessToken() != null) {
            LOG.info("Received ANSWER from session with id: " + session.getId());
            String deviceId = message.getAccessToken();
            UserAnswer payload = DeserializationUtils.deserializePayload(message.getPayload(), UserAnswer.class)
                .orElseThrow();
            answerService.saveUserAnswer(payload, deviceId);
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
}
