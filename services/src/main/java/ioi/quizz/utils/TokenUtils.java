package ioi.quizz.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.*;

import java.util.Base64;

public class TokenUtils {
    
    public static String getUserId(String token) {
        String[] parts = token.split("\\.");
        String claims = parts[1];
        String decoded = new String(Base64.getUrlDecoder().decode(claims));
        
        ObjectNode node = DeserializationUtils.deserializePayload(decoded, ObjectNode.class)
            .orElse(null);
        
        if (node == null) {
            return null;
        }
        
        return node.get("sub").textValue();
    }
    
}
