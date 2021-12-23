package ioi.quizz.interceptors;

import com.mjamsek.rest.exceptions.UnauthorizedException;
import ioi.quizz.config.QuizzConstants;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

@Interceptor
@RequireDeviceId
public class RequireDeviceIdInterceptor {

    @Context
    private HttpServletRequest httpRequest;
    
    @AroundInvoke
    public Object checkHeaderPresence(InvocationContext context) throws Exception {
        String header = httpRequest.getHeader(QuizzConstants.DEVICE_ID_HEADER);
        if (header == null) {
            throw new UnauthorizedException("Missing device id!");
        }
        return context.proceed();
    }
}
