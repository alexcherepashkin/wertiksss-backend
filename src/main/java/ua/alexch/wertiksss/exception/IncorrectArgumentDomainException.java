package ua.alexch.wertiksss.exception;

/**
 * Thrown when an incorrect argument has been passed to a method.
 */
public class IncorrectArgumentDomainException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IncorrectArgumentDomainException() {
        super();
    }

    public IncorrectArgumentDomainException(String message) {
        super(message);
    }

    public IncorrectArgumentDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
