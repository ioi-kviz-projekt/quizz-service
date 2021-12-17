package ioi.quizz.api.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ioi.quizz.producers.JacksonProducer;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
    
    @Override
    public ObjectMapper getContext(Class<?> aClass) {
        return JacksonProducer.getNewMapper();
    }
}
