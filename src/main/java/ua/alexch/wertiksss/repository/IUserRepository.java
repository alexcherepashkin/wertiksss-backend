package ua.alexch.wertiksss.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.alexch.wertiksss.model.User;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}
