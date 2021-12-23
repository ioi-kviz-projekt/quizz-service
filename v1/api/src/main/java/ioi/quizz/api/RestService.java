package ioi.quizz.api;

import ioi.quizz.api.endpoints.*;
import ioi.quizz.persistence.RoomEntity;

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
        classes.add(GroupEndpoint.class);
        classes.add(RoomEndpoint.class);
        classes.add(StudentEndpoint.class);
        classes.add(ThemeEndpoint.class);
        
        return classes;
    }
}
