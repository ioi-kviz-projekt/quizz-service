package ioi.quizz.api.endpoints;

import ioi.quizz.config.QuizzConstants;
import ioi.quizz.lib.QuizInstance;
import ioi.quizz.services.QuizService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/quizzes")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuizEndpoint {
    
    @Inject
    private QuizService quizService;
    
    @GET
    @Path("/{roomId}/active")
    public Response getActiveQuiz(@PathParam("roomId") String roomId,
                                  @HeaderParam(QuizzConstants.DEVICE_ID_HEADER) String deviceId) {
        QuizInstance activeQuiz = quizService.getActiveQuizState(deviceId, roomId);
        return Response.ok(activeQuiz).build();
    }
    
    @POST
    @Path("/{roomId}/new")
    public Response createQuizInstance(@PathParam("roomId") String roomId) {
        QuizInstance instance = quizService.createInstance(roomId);
        return Response.ok(instance).build();
    }
    
    @POST
    @Path("/start/{id}")
    public Response startQuizz(@PathParam("id") String id) {
        quizService.startQuiz(id);
        return Response.noContent().build();
    }
    
}
