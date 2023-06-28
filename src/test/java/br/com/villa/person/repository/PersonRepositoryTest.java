package br.com.villa.person.repository;

import br.com.villa.person.model.Address;
import br.com.villa.person.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
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
                "henrique.villar@example.com",
                Arrays.asList(new Address())
        );

        personRepository.save(person);

        Optional<Person> foundPersonOptional = personRepository.findById(person.getId());

        assertThat(foundPersonOptional).isPresent();

        Person foundPerson = foundPersonOptional.orElse(null);

        assertThat(foundPerson.getId()).isEqualTo(person.getId());
        assertThat(foundPerson.getName()).isEqualTo(person.getName());
        assertThat(foundPerson.getEmail()).isEqualTo(person.getEmail());
        assertThat(foundPerson.getCpf()).isEqualTo(person.getCpf());
        assertThat(foundPerson.getRg()).isEqualTo(person.getRg());
    }


    @Test
    public void should_find_all_people() {
        Person person = new Person(
                UUID.randomUUID(),
                "Henrique Villa",
                "12345678902",
                "123456711",
                "henrique.villa@ee.com",
                Arrays.asList(new Address())
        );

        Person person2 = new Person(
                UUID.randomUUID(),
                "Henrique Villarrazo",
                "12345678903",
                "123456700",
                "henrique.villarrazo@ee.com",
                Arrays.asList(new Address())
        );

        personRepository.save(person);
        personRepository.save(person2);

        List<Person> people = personRepository.findAllPeople();

        assertThat(people).contains(person, person2);
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

        var updatedPerson = person;
        updatedPerson.setName("Updated Name");
        updatedPerson.setCpf("11111111111");

        personRepository.save(updatedPerson);

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

        personRepository.delete(person);

        Person deletedPerson = entityManager.find(Person.class, person.getId());
        assertThat(deletedPerson).isNull();
    }
}