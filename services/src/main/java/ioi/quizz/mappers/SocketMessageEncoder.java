package ioi.quizz.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ioi.quizz.lib.ws.SocketMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class SocketMessageEncoder implements Encoder.Text<SocketMessage> {
    
    @Override
    public String encode(SocketMessage socketMessage) throws EncodeException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(socketMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void init(EndpointConfig endpointConfig) {
    
    }
    
    @Override
    public void destroy() {
    
    }
}
