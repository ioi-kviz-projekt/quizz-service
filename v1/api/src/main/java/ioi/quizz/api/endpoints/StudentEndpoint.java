package ioi.quizz.api.endpoints;

import ioi.quizz.config.QuizzConstants;
import ioi.quizz.interceptors.RequireDeviceId;
import ioi.quizz.lib.Student;
import ioi.quizz.lib.responses.CheckUserResponse;
import ioi.quizz.services.StudentService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/students")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentEndpoint {
    
    @Inject
    private StudentService studentService;
    
    @POST
    @Path("/status")
    @RequireDeviceId
    public Response checkDeviceStatus(@HeaderParam(QuizzConstants.DEVICE_ID_HEADER) String deviceId) {
        CheckUserResponse response = studentService.checkUser(deviceId);
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/info")
    @RequireDeviceId
    public Response getUserInfo(@HeaderParam(QuizzConstants.DEVICE_ID_HEADER) String deviceId) {
        Student student = studentService.getStudentByDevice(deviceId);
        return Response.ok(student).build();
    }
    
}
