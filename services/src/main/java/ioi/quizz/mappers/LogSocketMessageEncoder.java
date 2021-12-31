package ioi.quizz.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ioi.quizz.lib.ws.LogSocketMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class LogSocketMessageEncoder implements Encoder.Text<LogSocketMessage>{
    
    @Override
    public String encode(LogSocketMessage object) throws EncodeException {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void init(EndpointConfig config) {
    
    }
    
    @Override
    public void destroy() {
    
    }
}
