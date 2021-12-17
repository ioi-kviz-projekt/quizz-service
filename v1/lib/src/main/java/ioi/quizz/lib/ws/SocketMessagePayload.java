package ioi.quizz.lib.ws;

public class SocketMessagePayload<T> {
    
    private String accessToken;
    
    private T payload;
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public T getPayload() {
        return payload;
    }
    
    public void setPayload(T payload) {
        this.payload = payload;
    }
}
