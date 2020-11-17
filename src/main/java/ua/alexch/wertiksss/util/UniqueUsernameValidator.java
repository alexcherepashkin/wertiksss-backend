package ua.alexch.wertiksss.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ua.alexch.wertiksss.model.User;
import ua.alexch.wertiksss.repository.IUserRepository;

/**
 * Validator relies on the {@link IUserRepository} to check if a {@link User}
 * with a given name already exists in the database.
 */
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final IUserRepository userRepository;

    @Autowired
    public UniqueUsernameValidator(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        return !userRepository.existsByUsername(username);
    }
}
