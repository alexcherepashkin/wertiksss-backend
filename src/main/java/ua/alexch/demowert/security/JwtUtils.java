package ua.alexch.demowert.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utility class which provides methods for generating and processing JWTs.
 */
@Component
public class JwtUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${jwt.expirationInMs}")
    private long jwtExpirationInMs;

    public String generateJwt(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwt(String authToken) throws CredentialsExpiredException {
        try {
            Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(authToken);
            return true;

        } catch (JwtException ex) {
            LOGGER.error("JWT validation failed: {}", ex.getMessage());

            if (ex instanceof ExpiredJwtException) {
                throw new CredentialsExpiredException(ex.getMessage(), ex);
            }
            return false;
        }
    }

}
