package ua.alexch.wertiksss.exception;

import ua.alexch.wertiksss.model.User;

/**
 * Thrown when a service layer cannot find the {@link User} entity.
 */
public class UserEntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserEntityNotFoundException() {
        super();
    }

    public UserEntityNotFoundException(String message) {
        super(message);
    }
}
