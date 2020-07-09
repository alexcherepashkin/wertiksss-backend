package ua.alexch.demowert;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "SELECT * FROM accounts WHERE phone_number ILIKE %:number%", nativeQuery = true)
    public List<Person> findAllByPhoneNumber(@Param("number") String phoneNumber);

    @Query(value = "SELECT * FROM accounts WHERE last_name ILIKE %:name%", nativeQuery = true)
    public List<Person> findAllByLastName(@Param("name") String lastName);

    @Query(value = "SELECT * FROM accounts WHERE phone_model ILIKE %:model%", nativeQuery = true)
    public List<Person> findAllByPhoneModel(@Param("model") String phoneModel);

}
