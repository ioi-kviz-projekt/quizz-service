package ioi.quizz.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ioi.quizz.lib.ws.LogSocketMessage;
import ioi.quizz.lib.ws.SocketMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

public class LogSocketMessageDecoder implements Decoder.Text<LogSocketMessage>{
    
    @Override
    public LogSocketMessage decode(String s) throws DecodeException {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(s, LogSocketMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean willDecode(String s) {
        return true;
    }
    
    @Override
    public void init(EndpointConfig config) {
    
    }
    
    @Override
    public void destroy() {
    
    }
}
