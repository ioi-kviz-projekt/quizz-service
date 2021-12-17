package ioi.quizz.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import ioi.quizz.producers.JacksonProducer;

import java.io.IOException;
import java.util.Optional;

public class DeserializationUtils {
    
    private static final ObjectMapper objectMapper = JacksonProducer.getNewMapper();
    
    public static <T> Optional<T> deserializePayload(String payload, Class<T> type) {
        try {
            return Optional.of(objectMapper.readValue(payload, type));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
}
