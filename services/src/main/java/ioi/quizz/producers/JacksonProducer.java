package ioi.quizz.producers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonProducer {
    
    public static ObjectMapper getNewMapper() {
        ObjectMapper mapper = new ObjectMapper();
    
        mapper.registerModule(new JavaTimeModule());
    
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    
        return mapper;
    }
    
}
