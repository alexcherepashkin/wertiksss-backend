package ua.alexch.wertiksss.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Returns an appropriate error code with a message to the client (Forbidden).
 */
@Component
public class InsufficientAuthorityHandler implements AccessDeniedHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsufficientAuthorityHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        LOGGER.warn("User attempted to access the protected URI: {}", request.getRequestURI());

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int status = (auth != null)
                ? HttpServletResponse.SC_FORBIDDEN
                : HttpServletResponse.SC_UNAUTHORIZED;

        response.sendError(status, accessDeniedException.getMessage());
    }
}
