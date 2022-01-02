package ioi.quizz.api.endpoints;

import ioi.quizz.config.QuizzConstants;
import ioi.quizz.lib.requests.QuestionCreateRequest;
import ioi.quizz.services.QuestionService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/questions")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuestionEndpoint {
    
    @Inject
    private QuestionService questionService;
    
    @POST
    public Response addQuestion(
        @HeaderParam(QuizzConstants.DEVICE_ID_HEADER) String deviceId,
        QuestionCreateRequest request) {
        questionService.addQuestion(deviceId, request);
        return Response.noContent().build();
    }
    
}
