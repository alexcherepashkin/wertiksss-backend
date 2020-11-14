package ua.alexch.demowert.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.alexch.demowert.model.User;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}
