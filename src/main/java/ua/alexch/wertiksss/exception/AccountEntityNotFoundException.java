package ua.alexch.wertiksss.exception;

import ua.alexch.wertiksss.model.Account;

/**
 * Thrown when a service layer cannot find the {@link Account} entity.
 */
public class AccountEntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AccountEntityNotFoundException() {
        super();
    }

    public AccountEntityNotFoundException(String message) {
        super(message);
    }

    public AccountEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
