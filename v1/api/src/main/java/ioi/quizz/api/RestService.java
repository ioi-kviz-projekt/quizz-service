package ioi.quizz.api;

import ioi.quizz.api.endpoints.QuizEndpoint;
import ioi.quizz.api.endpoints.TestEndpoint;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class RestService extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        classes.add(TestEndpoint.class);
        classes.add(QuizEndpoint.class);
        
        return classes;
    }
}
