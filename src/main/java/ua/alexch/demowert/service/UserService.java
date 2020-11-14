package ua.alexch.demowert.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import ua.alexch.demowert.exception.UserEntityNotFoundException;
import ua.alexch.demowert.model.Role;
import ua.alexch.demowert.model.User;
import ua.alexch.demowert.repository.IUserRepository;

/**
 * Service layer for managing {@link User} objects.
 */
@Service
@Transactional(readOnly = true)
public class UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public User addNewUser(User user) {
        User newUser = new User();

        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setActive(true);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        if (CollectionUtils.isEmpty(user.getRoles())) {
            newUser.setRoles(Collections.singleton(Role.USER));
        } else {
            newUser.setRoles(user.getRoles());
        }

        return userRepository.save(newUser);
    }

    @PostAuthorize("hasAuthority('ADMIN') or returnObject eq principal")
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserEntityNotFoundException("Could not find user profile with ID=" + id));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public void removeById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserEntityNotFoundException(
                    String.format("Failed to remove user profile with ID=%d because it was not found", id));
        }
// TODO: change remove to -> isActive = false
        userRepository.deleteById(id);
    }

// TODO: need implementation -> public User updateUser(User user) {}
}
