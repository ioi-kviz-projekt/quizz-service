package ioi.quizz.api.endpoints;

import ioi.quizz.context.SocketSessionContext;
import ioi.quizz.lib.ws.SocketMessage;

import javax.enterprise.context.RequestScoped;
import javax.websocket.Session;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/test")
@RequestScoped
public class TestEndpoint {
    
    @GET
    public Response test() {
        Set<Session> sessions = SocketSessionContext.getAllSessions();
    
        SocketMessage message = new SocketMessage();
        message.setPayload("test");
        message.setClassName("java.lang.String");
        sessions.forEach(session -> {
            session.getAsyncRemote().sendObject(message);
        });
        return Response.noContent().build();
    }
    
}
