package ua.alexch.wertiksss.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enumeration that represents the role (an authority) of the application user.
 */
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
