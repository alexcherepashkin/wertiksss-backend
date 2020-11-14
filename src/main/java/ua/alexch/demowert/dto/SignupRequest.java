package ua.alexch.demowert.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO class for {@code User} registration request.
 */
public class SignupRequest {
    @NotBlank(message = "Username is required")
//    Username must be no more than 20 characters
    @Size(max = 20, message = "Username must be at most 20 characters")
//    @UniqueUsername(message = "Username already exists")
    private String username;

    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    private Set<String> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
