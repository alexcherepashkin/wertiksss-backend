package ua.alexch.demowert.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ua.alexch.demowert.dto.JwtResponse;
import ua.alexch.demowert.dto.LoginRequest;
import ua.alexch.demowert.dto.MessageResponse;
import ua.alexch.demowert.dto.SignupRequest;
import ua.alexch.demowert.model.User;
import ua.alexch.demowert.security.JwtUtils;
import ua.alexch.demowert.service.UserService;
import ua.alexch.demowert.util.RoleUtils;

/**
 * REST controller for {@link User} authentication/registration.
 * 
 * @author Alexey Cherepashkin.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthUserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthUserController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (!userService.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with name '%s' not found", username));
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = jwtUtils.generateJwt(authentication);
        User authUser = (User) authentication.getPrincipal();
        Set<String> roles = RoleUtils.convertToRoleNames(authUser.getRoles());

        JwtResponse response = new JwtResponse(token, authUser.getId(), authUser.getUsername(), roles);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userService.existsByUsername(signupRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists!");
        }

        User newUser = new User();
        BeanUtils.copyProperties(signupRequest, newUser, "roles");

        Set<String> roleNames = signupRequest.getRoles();
        if (!CollectionUtils.isEmpty(roleNames)) {
            newUser.setRoles(RoleUtils.convertToRoleEnums(roleNames));
        }

        userService.addNewUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("You have registered successfully!"));
    }
}
