package ioi.quizz.api.startup;

import com.fasterxml.jackson.databind.ObjectMapper;
import ioi.quizz.producers.JacksonProducer;
import ioi.quizz.runnables.QuizStateHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class StartupListener implements ServletContextListener {
    
    private ScheduledExecutorService executorService;
    
    private QuizStateHandler handler;
    
    @PostConstruct
    private void init() {
        this.executorService = Executors.newScheduledThreadPool(1);
        this.handler = CDI.current().select(QuizStateHandler.class).get();
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.executorService.scheduleAtFixedRate(handler, 0, 1, TimeUnit.SECONDS);
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    
    }
}
