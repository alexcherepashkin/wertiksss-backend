package ua.alexch.demowert.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.alexch.demowert.model.Account;

/**
 * Spring Data JPA repository for the {@link Account} entity.
 */
@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {

//  public List<Account> findByAndSort(User owner, Sort sort);
//  public List<Account> findByOwnerOrderByIdDesc(User owner);
    @Query(value = "SELECT a FROM Account a WHERE a.owner.id=:userId ORDER BY a.id DESC")
    public List<Account> findAllForUserSortedByIdDesc(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM acc WHERE phone_number ILIKE %:number% AND owner_id=:userId", nativeQuery = true)
    public List<Account> findAllByPhoneNumber(@Param("number") String phoneNumber, @Param("userId") Long userId);

    @Query(value = "SELECT * FROM acc WHERE last_name ILIKE %:name% AND owner_id=:userId", nativeQuery = true)
    public List<Account> findAllByLastName(@Param("name") String lastName, @Param("userId") Long userId);

    @Query(value = "SELECT * FROM acc WHERE phone_model ILIKE %:model% AND owner_id=:userId", nativeQuery = true)
    public List<Account> findAllByPhoneModel(@Param("model") String phoneModel, @Param("userId") Long userId);

    @Query(value = "SELECT a FROM Account a WHERE a.id=:id AND a.owner.id=:userId")
    public Optional<Account> findByIdForUser(@Param("id") Long id, @Param("userId") Long userId);

}
