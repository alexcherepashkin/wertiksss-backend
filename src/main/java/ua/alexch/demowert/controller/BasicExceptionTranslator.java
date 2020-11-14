package ua.alexch.demowert.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ua.alexch.demowert.dto.ErrorResponse;
import ua.alexch.demowert.exception.AccountEntityNotFoundException;
import ua.alexch.demowert.exception.IncorrectArgumentDomainException;
import ua.alexch.demowert.exception.UserEntityNotFoundException;

/**
 * Controller advice to translate server-side exceptions to client-friendly structures.
 * 
 * @author Alexey Cherepashkin.
 */
@RestControllerAdvice
public class BasicExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AccountEntityNotFoundException.class)
    public ResponseEntity<Object> handleAccountEntityNotFound(AccountEntityNotFoundException ex, WebRequest request) {
        log(ex);
        final ErrorResponse body = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(), body.getStatus(), request);
    }

    @ExceptionHandler(value = UserEntityNotFoundException.class)
    public ResponseEntity<Object> handleUserEntityNotFound(UserEntityNotFoundException ex, WebRequest request) {
        log(ex);
        final ErrorResponse body = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(), body.getStatus(), request);
    }

    @ExceptionHandler(value = IncorrectArgumentDomainException.class)
    public ResponseEntity<Object> handleIncorrectArgument(IncorrectArgumentDomainException ex, WebRequest request) {
        log(ex);
        final ErrorResponse body = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(), body.getStatus(), request);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        log(ex);
        final ErrorResponse body = new ErrorResponse(HttpStatus.BAD_REQUEST, "Validation error..");

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            body.addValidationError(violation.getRootBeanClass().getName(), violation.getPropertyPath().toString(),
                    violation.getMessage());
        }

        return handleExceptionInternal(ex, body, new HttpHeaders(), body.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        log(ex);
        final ErrorResponse body = new ErrorResponse(status, "Validation error..");

        ex.getBindingResult().getFieldErrors()
            .forEach(fieldError ->
                body.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage())
            );

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        log(ex);
        final ErrorResponse body;
        final StringBuilder message = new StringBuilder();

        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();

        if (!CollectionUtils.isEmpty(supportedMethods)) {
            message.append("Method ");
            message.append(ex.getMethod());
            message.append(" is not supported for this request. Supported methods are:");

            supportedMethods.forEach(method -> message.append(" " + method));

            headers.setAllow(supportedMethods);
        }
        body = new ErrorResponse(status, message.toString());

        return handleExceptionInternal(ex, body, headers, status, request);
    }

//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {
//        log(ex);
//        final ResponseStatus status = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
//
//        final ErrorResponse body = new ErrorResponse(
//                (status != null) ? status.code() : HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//
//        return handleExceptionInternal(ex, body, new HttpHeaders(), body.getStatus(), request);
//    }

    private void log(Exception ex) {
        logger.info("Handle an exception: " + ex.getClass().getName());
    }
}
