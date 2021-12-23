package ioi.quizz.api.endpoints;

import ioi.quizz.interceptors.RequireDeviceId;
import ioi.quizz.lib.GroupWithMembers;
import ioi.quizz.services.GroupService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/groups")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupEndpoint {
    
    @Inject
    private GroupService groupService;
    
    @GET
    @RequireDeviceId
    public Response joinRoom(@HeaderParam("X-Device-Id") String deviceId) {
        GroupWithMembers group = groupService.getUserGroup(deviceId);
        return Response.ok(group).build();
    }
}