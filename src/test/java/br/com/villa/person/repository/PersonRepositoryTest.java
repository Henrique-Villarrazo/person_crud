package br.com.villa.person.repository;

import br.com.villa.person.model.Address;
import br.com.villa.person.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void should_save_person() {
        Person person = new Person(
                UUID.randomUUID(),
                "Henrique Villa",
                "12345678900",
                "123456789",
                "henrique.villa@example.com",
                Arrays.asList(new Address())
        );
        personRepository.save(person);

        assertThat(person.getId()).isNotNull();
    }

    @Test
    public void should_find_person_by_id() {
        Person person = new Person(
                UUID.randomUUID(),
                "Henrique Villa",
                "12345678900",
                "123456789",
                "henrique.villa@example.com",
                Arrays.asList(new Address())
        );
        entityManager.persist(person);
        entityManager.flush();

        Person foundPerson = personRepository.findById(person.getId()).orElse(null);

        assertThat(foundPerson).isNotNull();
        assertThat(foundPerson.getId()).isEqualTo(person.getId());
        assertThat(foundPerson.getName()).isEqualTo(person.getName());
    }

    @Test
    public void should_find_all_persons() {
        Person person = new Person(
                UUID.randomUUID(),
                "Henrique Villa",
                "12345678900",
                "123456789",
                "henrique.villa@example.com",
                Arrays.asList(new Address())
        );
        Person person2 = new Person(
                UUID.randomUUID(),
                "Henrique Villarrazo",
                "12345678901",
                "123456781",
                "henrique.villarrazo@example.com",
                Arrays.asList(new Address())
        );
        entityManager.persist(person);
        entityManager.persist(person2);
        entityManager.flush();

        List<Person> persons = personRepository.findAll();

        assertThat(persons).hasSize(2);
        assertThat(persons).contains(person, person2);
    }

    @Test
    public void should_update_person() {
        Person person = new Person(
                UUID.randomUUID(),
                "Henrique Villa",
                "12345678900",
                "123456789",
                "henrique.villa@example.com",
                Arrays.asList(new Address())
        );
        entityManager.persist(person);
        entityManager.flush();

        person.setName("Updated Name");
        person.setCpf("11111111111");
        personRepository.save(person);

        Person updatedPerson = entityManager.find(Person.class, person.getId());

        assertThat(updatedPerson.getName()).isEqualTo("Updated Name");
        assertThat(updatedPerson.getCpf()).isEqualTo("11111111111");
    }

    @Test
    public void should_delete_person() {
        Person person = new Person(
                UUID.randomUUID(),
                "Henrique Villa",
                "12345678900",
                "123456789",
                "henrique.villa@example.com",
                Arrays.asList(new Address())
        );
        entityManager.persist(person);
        entityManager.flush();

        personRepository.delete(person);

        Person deletedPerson = entityManager.find(Person.class, person.getId());
        assertThat(deletedPerson).isNull();
    }
}
