package br.com.villa.person.repository;

import br.com.villa.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findById(UUID uuid);

    @Query("SELECT p FROM Person p")
    List<Person> findAllPeople();

}
