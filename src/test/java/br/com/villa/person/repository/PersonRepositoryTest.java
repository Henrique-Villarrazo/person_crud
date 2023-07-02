package br.com.villa.person.repository;

import br.com.villa.person.model.Address;
import br.com.villa.person.model.Person;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Mock
    private PersonRepository personRepository;

    public PersonRepositoryTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_save_person() {
        Person person = new Person(
                UUID.randomUUID(),
                "Henrique Villa",
                "12345678900",
                "123456789",
                "henrique.villa@example.com",
                Collections.singleton(new Address())
        );
        personRepository.save(person);

        assertThat(person.getId()).isNotNull();
    }


    @Test
    public void should_find_person_by_id() {
        // Create a sample person
        Person person = new Person(
                UUID.randomUUID(),
                "Henrique Villa",
                "12345678900",
                "123456789",
                "henrique.villar@example.com",
                Collections.singleton(new Address())
        );

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        Optional<Person> foundPersonOptional = personRepository.findById(person.getId());

        assertThat(foundPersonOptional).isPresent();
        Person foundPerson = foundPersonOptional.orElse(null);
        assertThat(foundPerson.getId()).isEqualTo(person.getId());
        assertThat(foundPerson.getName()).isEqualTo(person.getName());
        assertThat(foundPerson.getEmail()).isEqualTo(person.getEmail());
        assertThat(foundPerson.getCpf()).isEqualTo(person.getCpf());
        assertThat(foundPerson.getRg()).isEqualTo(person.getRg());

        verify(personRepository, times(1)).findById(person.getId());
        verifyNoMoreInteractions(personRepository);
        }

        @Test
        public void should_find_all_people() {
            // Create sample people
            Person person1 = new Person(
                    UUID.randomUUID(),
                    "Henrique Villa",
                    "12345678902",
                    "123456711",
                    "henrique.villa@ee.com",
                    Collections.singleton(new Address())
            );

            Person person2 = new Person(
                    UUID.randomUUID(),
                    "Henrique Villarrazo",
                    "12345678903",
                    "123456700",
                    "henrique.villarrazo@ee.com",
                    Collections.singleton(new Address())
            );

            List<Person> expectedPeople = Arrays.asList(person1, person2);
            when(personRepository.findAllPeople()).thenReturn(expectedPeople);

            List<Person> people = personRepository.findAllPeople();

            assertThat(people).containsExactlyInAnyOrderElementsOf(expectedPeople);

            verify(personRepository, times(1)).findAllPeople();
            verifyNoMoreInteractions(personRepository);
        }

    @Test
    public void should_update_person() {
        Person person = new Person(
                UUID.randomUUID(),
                "Henrique Villa",
                "12345678900",
                "123456789",
                "henrique.villa@example.com",
                Collections.singleton(new Address())
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
                Collections.singleton(new Address())
        );

        personRepository.delete(person);

        Person deletedPerson = entityManager.find(Person.class, person.getId());
        assertThat(deletedPerson).isNull();
    }
}