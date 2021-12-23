package ioi.quizz.api.endpoints;

import com.mjamsek.auth.common.annotations.AuthenticatedAllowed;
import com.mjamsek.auth.common.annotations.PublicResource;
import com.mjamsek.auth.common.annotations.SecureResource;
import com.mjamsek.auth.context.AuthContext;
import ioi.quizz.config.QuizzConstants;
import ioi.quizz.interceptors.RequireDeviceId;
import ioi.quizz.lib.Room;
import ioi.quizz.lib.requests.RoomRegistrationRequest;
import ioi.quizz.services.RoomService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rooms")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecureResource
public class RoomEndpoint {
    
    @Inject
    private RoomService roomService;
    
    @Inject
    private AuthContext authContext;
    
    @POST
    @AuthenticatedAllowed
    public Response createRoom() {
        Room createdRoom = roomService.createRoom(authContext.getId());
        return Response.ok(createdRoom).build();
    }
    
    @POST
    @Path("/join")
    @RequireDeviceId
    @PublicResource
    public Response joinRoom(@HeaderParam(QuizzConstants.DEVICE_ID_HEADER) String deviceId, RoomRegistrationRequest request) {
        request.setDeviceId(deviceId);
        Room joinedRoom = roomService.registerUserToRoom(request);
        return Response.ok(joinedRoom).build();
    }
}
