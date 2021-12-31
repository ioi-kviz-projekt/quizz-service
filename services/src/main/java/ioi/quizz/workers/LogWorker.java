package ioi.quizz.workers;

import ioi.quizz.lib.ws.LogSocketMessage;
import ioi.quizz.services.LogService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;

@ApplicationScoped
public class LogWorker {
    
    @Inject
    private LogService logService;
    
    @ActivateRequestContext
    public void handleLogSocketMessage(LogSocketMessage message) {
        logService.logEntry(message.getSeverity(), message.getMessage());
    }
    
}
