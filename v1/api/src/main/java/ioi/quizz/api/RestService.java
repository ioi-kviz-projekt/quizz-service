package ioi.quizz.api;

import ioi.quizz.api.endpoints.*;
import ioi.quizz.api.mappers.GenericExceptionMapper;
import ioi.quizz.api.mappers.NotFoundExceptionMapper;
import ioi.quizz.api.mappers.RestExceptionMapper;
import ioi.quizz.api.mappers.ValidationExceptionMapper;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class RestService extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        classes.add(QuizEndpoint.class);
        classes.add(GroupEndpoint.class);
        classes.add(RoomEndpoint.class);
        classes.add(StudentEndpoint.class);
        classes.add(ThemeEndpoint.class);
        classes.add(QuestionEndpoint.class);
        
        classes.add(RestExceptionMapper.class);
        classes.add(NotFoundExceptionMapper.class);
        classes.add(GenericExceptionMapper.class);
        classes.add(ValidationExceptionMapper.class);
        
        return classes;
    }
}
