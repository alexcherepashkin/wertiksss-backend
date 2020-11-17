package ua.alexch.wertiksss.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.alexch.wertiksss.model.User;
import ua.alexch.wertiksss.repository.IUserRepository;

/**
 * Basic implementation of the {@link UserDetailsService} interface that loads
 * {@link User} data during an authentication attempt.
 */
@Service
public class BasicUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicUserDetailsService.class);

    private final IUserRepository userRepository;

    @Autowired
    public BasicUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        LOGGER.info("Authentication attempt.. Username: '{}'", username);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

//        Optional<User> userFromDb = userRepository.findByUsername(username);
//
//        return userFromDb.map(user -> {
//            if (!user.isActive()) {
//                throw new DisabledException("User '" + username + "' has been deleted");
//            }
//            return user;
//
//        }).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}
