package ioi.quizz.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import ioi.quizz.producers.JacksonProducer;

import java.io.IOException;
import java.util.Optional;

public class SerializationUtils {
    
    private static final ObjectMapper objectMapper = JacksonProducer.getNewMapper();
    
    public static <T> Optional<String> serializePayload(T payload) {
        try {
            return Optional.of(objectMapper.writeValueAsString(payload));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
}
