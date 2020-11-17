package ua.alexch.wertiksss.util;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import ua.alexch.wertiksss.model.User;

/**
 * Utility class that provides methods for operations with the Security context.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (User) authentication.getPrincipal();
        } else {
            throw new AuthenticationCredentialsNotFoundException("User is not authenticated!");
        }
    }

    public static boolean isNotTheCurrentUser(User user) {
        return !getCurrentUser().equals(user);
    }

}
