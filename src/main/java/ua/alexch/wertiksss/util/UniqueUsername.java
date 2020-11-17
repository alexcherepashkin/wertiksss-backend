package ua.alexch.wertiksss.util;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ua.alexch.wertiksss.model.User;

/**
 * Annotation used on a class field that represents the {@link User} model to
 * validate its uniqueness by name.
 */
@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ ElementType.FIELD })
@Retention(RUNTIME)
public @interface UniqueUsername {

    String message() default "Username already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
