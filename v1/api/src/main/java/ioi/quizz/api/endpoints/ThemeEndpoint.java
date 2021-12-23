package ioi.quizz.api.endpoints;

import ioi.quizz.config.QuizzConstants;
import ioi.quizz.interceptors.RequireDeviceId;
import ioi.quizz.lib.DiscoverableTheme;
import ioi.quizz.services.ThemeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/themes")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ThemeEndpoint {
    
    @Inject
    private ThemeService themeService;
    
    @GET
    @RequireDeviceId
    public Response getThemes(@HeaderParam(QuizzConstants.DEVICE_ID_HEADER) String deviceId) {
        List<DiscoverableTheme> themes = themeService.getThemes(deviceId);
        return Response.ok(themes).build();
    }
    
    @POST
    @Path("/{themeId}")
    public Response discoverTheme(@PathParam("themeId") String themeId,
                                  @HeaderParam(QuizzConstants.DEVICE_ID_HEADER) String deviceId) {
        themeService.discoverTheme(themeId, deviceId);
        return Response.noContent().build();
    }
    
}
