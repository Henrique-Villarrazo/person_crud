package br.com.villa.person.service;

import br.com.villa.person.dto.PersonDTO;
import br.com.villa.person.model.Address;
import br.com.villa.person.model.Person;
import br.com.villa.person.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    public void testCreatePerson() {
        UUID expectedId = UUID.fromString("831681ff-5b93-4306-8e0d-a7d3743e6966");
        PersonDTO personDTO = new PersonDTO(expectedId, "Henrique villa", "12345678900", "123456789", "henrique.villa@example.com",
                Collections.singleton(new Address("Street 1", "District 1", "12345678", "123", "Complement 1", "City 1", "UF 1", new Person(expectedId))));
        Person person = new Person(personDTO);

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        when(personRepository.save(personCaptor.capture())).thenAnswer(invocation -> {
            Person savedPerson = invocation.getArgument(0);
            savedPerson.setId(expectedId);
            return savedPerson;
        });

        Person createdPerson = personService.createPerson(personDTO);

        assertNotNull(createdPerson);
        assertEquals(expectedId, createdPerson.getId());
        assertEquals(personDTO.name(), createdPerson.getName());
        assertEquals(personDTO.cpf(), createdPerson.getCpf());
        assertEquals(personDTO.rg(), createdPerson.getRg());
        assertEquals(personDTO.email(), createdPerson.getEmail());

        Person capturedPerson = personCaptor.getValue();
        assertEquals(expectedId, capturedPerson.getId());
        assertEquals(personDTO.name(), capturedPerson.getName());
        assertEquals(personDTO.cpf(), capturedPerson.getCpf());
        assertEquals(personDTO.rg(), capturedPerson.getRg());
        assertEquals(personDTO.email(), capturedPerson.getEmail());

        Address expectedAddress = personDTO.address().iterator().next();
        Address createdAddress = createdPerson.getAddress().iterator().next();
        assertEquals(expectedAddress.getStreet(), createdAddress.getStreet());
        assertEquals(expectedAddress.getDistrict(), createdAddress.getDistrict());
        assertEquals(expectedAddress.getCep(), createdAddress.getCep());
        assertEquals(expectedAddress.getNumber(), createdAddress.getNumber());
        assertEquals(expectedAddress.getComplement(), createdAddress.getComplement());
        assertEquals(expectedAddress.getCity(), createdAddress.getCity());
        assertEquals(expectedAddress.getUf(), createdAddress.getUf());
    }

    @Test
    public void testListAllPeople() {
        Person person1 = new Person(UUID.randomUUID(), "Henrique villa", "12345678900", "123456789", "henrique.villa@example.com",
                Collections.singleton(new Address("Street 1", "District 1", "12345678", "123", "Complement 1", "City 1", "UF 1", new Person(UUID.randomUUID()))));
        Person person2 = new Person(UUID.randomUUID(), "Carlos oliveira", "98765432100", "567894123", "carlos.oliveira@example.com",
                Collections.singleton(new Address("Street 2", "District 2", "87654321", "456", "Complement 2", "City 2", "UF 2", new Person(UUID.randomUUID()))));
        List<Person> people = Arrays.asList(person1, person2);

        when(personRepository.findAll()).thenReturn(people);

        Set<PersonDTO> allPeople = personService.listAllPeople();

        assertNotNull(allPeople);
        assertEquals(2, allPeople.size());

        PersonDTO personDTO1 = allPeople.stream()
                .filter(p -> p.id().equals(person1.getId()))
                .findFirst()
                .orElse(null);
        assertNotNull(personDTO1);
        assertEquals(person1.getId(), personDTO1.id());
        assertEquals(person1.getName(), personDTO1.name());
        assertEquals(person1.getCpf(), personDTO1.cpf());
        assertEquals(person1.getRg(), personDTO1.rg());
        assertEquals(person1.getEmail(), personDTO1.email());

        PersonDTO personDTO2 = allPeople.stream()
                .filter(p -> p.id().equals(person2.getId()))
                .findFirst()
                .orElse(null);
        assertNotNull(personDTO2);
        assertEquals(person2.getId(), personDTO2.id());
        assertEquals(person2.getName(), personDTO2.name());
        assertEquals(person2.getCpf(), personDTO2.cpf());
        assertEquals(person2.getRg(), personDTO2.rg());
        assertEquals(person2.getEmail(), personDTO2.email());

    }

    @Test
    public void testUpdatePerson() {
        UUID personId = UUID.fromString("831681ff-5b93-4306-8e0d-a7d3743e6966");
        PersonDTO personDTO = new PersonDTO(personId, "henrique.villa", "12345678900", "123456789", "henrique.villa@example.com",
                Collections.singleton(new Address()));
        Person existingPerson = new Person(personId, "Carlos Oliveira", "98765432100", "567894123", "carlos.oliveira@example.com",
                Collections.singleton(new Address()));

        when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).then(AdditionalAnswers.returnsFirstArg());

        Person updatedPerson = personService.updatePerson(personId, personDTO);

        assertNotNull(updatedPerson);
        assertEquals(personId, updatedPerson.getId());
        assertEquals(personDTO.name(), updatedPerson.getName());
        assertEquals(personDTO.cpf(), updatedPerson.getCpf());
        assertEquals(personDTO.rg(), updatedPerson.getRg());
        assertEquals(personDTO.email(), updatedPerson.getEmail());

        Address expectedAddress = personDTO.address().iterator().next();
        Address updatedAddress = updatedPerson.getAddress().iterator().next();
        assertEquals(expectedAddress.getStreet(), updatedAddress.getStreet());
        assertEquals(expectedAddress.getDistrict(), updatedAddress.getDistrict());
        assertEquals(expectedAddress.getCep(), updatedAddress.getCep());
        assertEquals(expectedAddress.getNumber(), updatedAddress.getNumber());
        assertEquals(expectedAddress.getComplement(), updatedAddress.getComplement());
        assertEquals(expectedAddress.getCity(), updatedAddress.getCity());
        assertEquals(expectedAddress.getUf(), updatedAddress.getUf());
    }

    @Test
    public void testDeletePerson() {
        UUID personId = UUID.fromString("831681ff-5b93-4306-8e0d-a7d3743e6966");
        Person existingPerson = new Person(personId, "Henrique villa", "12345678900", "123456789", "henrique.villa@example.com",
                Collections.singleton(new Address()));

        when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));

        personService.deletePerson(personId);

        verify(personRepository, times(1)).delete(existingPerson);
    }
}
