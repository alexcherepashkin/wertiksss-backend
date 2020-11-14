package ua.alexch.demowert.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * DTO for transferring an error message with (if exist) domain object
 * validation errors.
 */
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private List<ValidationError> validErrors;

    private ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(HttpStatus status) {
        this();
        this.status = status;
    }

    public ErrorResponse(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(HttpStatus status, String message, List<ValidationError> errors) {
        this();
        this.status = status;
        this.message = message;
        this.validErrors = errors;
    }

    public void addValidationError(String objectName, String field, String message) {
        if (validErrors == null) {
            validErrors = new ArrayList<>();
        }
        validErrors.add(new ValidationError(objectName, field, message));
    }

    public List<ValidationError> getValidationErrors() {
        return validErrors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    static class ValidationError {
        private final String objectName;
        private final String field;
        private final String message;

        public ValidationError(String field, String message) {
            this.objectName = null;
            this.field = field;
            this.message = message;
        }

        public ValidationError(String objectName, String field, String message) {
            this.objectName = objectName;
            this.field = field;
            this.message = message;
        }

        public String getObjectName() {
            return objectName;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
