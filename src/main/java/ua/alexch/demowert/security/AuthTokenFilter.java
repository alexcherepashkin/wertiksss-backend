package ua.alexch.demowert.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import ua.alexch.demowert.WertOptions;

/**
 * Filter class that is executed once per request to attempt to set
 * authentication principal using JWT, if a header is found in the HTTP request
 * that corresponding to a valid token.
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

    private static final String JWT_HEADER_NAME = "Authorization";
    private static final String JWT_PREFIX = "Bearer ";

    private final JwtUtils jwtUtils;
    private final UserDetailsService userService;

    @Autowired
    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = parseJwt(request);

            if (StringUtils.hasText(token) && jwtUtils.validateJwt(token)) {
                String username = jwtUtils.getUsernameFromJwt(token);
                UserDetails userDetails = userService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, token, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            LOGGER.error("Cannot set user authentication: {}", ex.getMessage());

            if (ex instanceof CredentialsExpiredException) {
                request.setAttribute(WertOptions.JWT_EXPIRED, ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(JWT_HEADER_NAME);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(JWT_PREFIX)) {
            return authHeader.substring(7, authHeader.length());
        }

        return "";
    }
}
