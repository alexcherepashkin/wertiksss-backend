package ua.alexch.wertiksss.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.alexch.wertiksss.dto.MessageResponse;
import ua.alexch.wertiksss.model.User;
import ua.alexch.wertiksss.service.UserService;

/**
 * REST controller for managing {@link User} profiles.
 * 
 * @author Alexey Cherepashkin.
 */
@RestController
@RequestMapping(path = "/main/usrs")
@CrossOrigin
public class AdminProtectedUserController {
    private final UserService userService;

    @Autowired
    public AdminProtectedUserController(UserService uService) {
        this.userService = uService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.findById(id);

        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<User> saveUserProfile(@Valid @RequestBody User user) {
        User savedUser = userService.addNewUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> removeUserProfile(@PathVariable("id") Long id) {
        userService.removeById(id);

        return ResponseEntity.ok(new MessageResponse("User profile was successfully deleted"));
    }

}
