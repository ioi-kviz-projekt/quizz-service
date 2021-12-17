package ioi.quizz.api.endpoints;

import ioi.quizz.lib.QuizInstance;
import ioi.quizz.lib.requests.QuizPasskey;
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
    
    @POST
    public Response getQuiz(QuizPasskey passkey) {
        return Response.ok(quizService.getQuizByPasskey(passkey)).build();
    }
    
    @POST
    @Path("/new")
    public Response createQuizInstance() {
        QuizInstance instance = quizService.createInstance();
        return Response.ok(instance).build();
    }
    
    @POST
    @Path("/start/{id}")
    public Response startQuizz(@PathParam("id") String id) {
        quizService.startQuiz(id);
        return Response.noContent().build();
    }
    
    /*@GET
    @Path("/summary/{id}")
    public Response getQuizzSummary(@PathParam("id") String id) {
    
    }*/
}
