package ua.alexch.demowert.dto;

import java.util.Set;

/**
 * DTO class for transferring a response with access JWT data.
 */
public class JwtResponse {
    private String token;
    private String tokenType;
    private Long userId;
    private String username;
    private Set<String> roles;

    public JwtResponse() {
        this.tokenType = "Bearer";
    }

    public JwtResponse(String token, Long id, String username, Set<String> roles) {
        this();
        this.token = token;
        this.userId = id;
        this.username = username;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

}
