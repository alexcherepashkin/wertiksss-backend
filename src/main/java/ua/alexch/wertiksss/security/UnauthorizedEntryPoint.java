package ua.alexch.wertiksss.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import ua.alexch.wertiksss.WertOptions;

/**
 * Returns an appropriate error code with a message to the client when an
 * {@code AuthenticationException} occurs (Unauthorized).
 */
@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnauthorizedEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        LOGGER.warn("Pre-authenticated entry point called.. Cause: {}", authException.getMessage());

        final String attribute = (String) request.getAttribute(WertOptions.JWT_EXPIRED);
        final String errorMessage;
        int status = HttpServletResponse.SC_UNAUTHORIZED;

        if (authException instanceof BadCredentialsException) {
            errorMessage = "Incorrect username or password";
            status = HttpServletResponse.SC_BAD_REQUEST;

        } else if (authException instanceof DisabledException) {
            errorMessage = "This user profile has been deleted";
            status = HttpServletResponse.SC_NOT_FOUND;

        } else {
            errorMessage = (attribute != null) ? attribute : authException.getMessage();
        }

        response.sendError(status, errorMessage);
    }
}
