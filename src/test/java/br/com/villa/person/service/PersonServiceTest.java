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

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        PersonDTO personDTO = new PersonDTO(expectedId, "Henrique villa", "12345678900", "123456789", "henrique.villa@example.com", Arrays.asList(
                new Address("Street 1", "District 1", "12345678", "123", "Complement 1", "City 1", "UF 1")));
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

        Address expectedAddress = personDTO.address().get(0);
        Address createdAddress = createdPerson.getAddress().get(0);
        assertEquals(expectedAddress.getStreet(), createdAddress.getStreet());
        assertEquals(expectedAddress.getDistrict(), createdAddress.getDistrict());
        assertEquals(expectedAddress.getCep(), createdAddress.getCep());
        assertEquals(expectedAddress.getNumber(), createdAddress.getNumber());
        assertEquals(expectedAddress.getComplement(), createdAddress.getComplement());
        assertEquals(expectedAddress.getCity(), createdAddress.getCity());
        assertEquals(expectedAddress.getUf(), createdAddress.getUf());
    }


    @Test
    public void testListAllPerson() {
        Person person1 = new Person(UUID.randomUUID(), "Henrique villa", "12345678900", "123456789", "henrique.villa@example.com",
                Arrays.asList(new Address("Street 1", "District 1", "12345678", "123", "Complement 1", "City 1", "UF 1")));
        Person person2 = new Person(UUID.randomUUID(), "Carlos oliveira", "98765432100", "567894123", "carlos.oliveira@example.com",
                Arrays.asList(new Address("Street 2", "District 2", "87654321", "456", "Complement 2", "City 2", "UF 2")));

        List<Person> people = Arrays.asList(person1, person2);

        when(personRepository.findAll()).thenReturn(people);

        List<PersonDTO> allPeople = personService.listAllPerson();

        assertNotNull(allPeople);
        assertEquals(people.size(), allPeople.size());

        PersonDTO personDTO1 = allPeople.get(0);
        assertEquals(person1.getId(), personDTO1.id());
        assertEquals(person1.getName(), personDTO1.name());
        assertEquals(person1.getCpf(), personDTO1.cpf());
        assertEquals(person1.getRg(), personDTO1.rg());
        assertEquals(person1.getEmail(), personDTO1.email());

        Address addressDTO1 = personDTO1.address().get(0);
        Address address1 = person1.getAddress().get(0);
        assertEquals(address1.getStreet(), addressDTO1.getStreet());
        assertEquals(address1.getDistrict(), addressDTO1.getDistrict());
        assertEquals(address1.getCep(), addressDTO1.getCep());
        assertEquals(address1.getNumber(), addressDTO1.getNumber());
        assertEquals(address1.getComplement(), addressDTO1.getComplement());
        assertEquals(address1.getCity(), addressDTO1.getCity());
        assertEquals(address1.getUf(), addressDTO1.getUf());

        PersonDTO personDTO2 = allPeople.get(1);
        assertEquals(person2.getId(), personDTO2.id());
        assertEquals(person2.getName(), personDTO2.name());
        assertEquals(person2.getCpf(), personDTO2.cpf());
        assertEquals(person2.getRg(), personDTO2.rg());
        assertEquals(person2.getEmail(), personDTO2.email());

        Address addressDTO2 = personDTO2.address().get(0);
        Address address2 = person2.getAddress().get(0);
        assertEquals(address2.getStreet(), addressDTO2.getStreet());
        assertEquals(address2.getDistrict(), addressDTO2.getDistrict());
        assertEquals(address2.getCep(), addressDTO2.getCep());
        assertEquals(address2.getNumber(), addressDTO2.getNumber());
        assertEquals(address2.getComplement(), addressDTO2.getComplement());
        assertEquals(address2.getCity(), addressDTO2.getCity());
        assertEquals(address2.getUf(), addressDTO2.getUf());

        verify(personRepository, times(1)).findAll();
    }


    @Test
    public void testFindPersonById() {
        UUID personId = UUID.randomUUID();
        Person person = new Person(personId, "Henrique villa", "12345678900", "123456789", "henrique.villa@example.com", Arrays.asList(new Address()));

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        PersonDTO foundPerson = personService.getPersonById(personId);

        assertNotNull(foundPerson);
        assertEquals(person.getId(), foundPerson.id());
        assertEquals(person.getName(), foundPerson.name());
        assertEquals(person.getCpf(), foundPerson.cpf());
        assertEquals(person.getRg(), foundPerson.rg());
        assertEquals(person.getEmail(), foundPerson.email());

        Address expectedAddress = person.getAddress().get(0);
        Address foundAddress = foundPerson.address().get(0);
        assertEquals(expectedAddress.getStreet(), foundAddress.getStreet());
        assertEquals(expectedAddress.getDistrict(), foundAddress.getDistrict());
        assertEquals(expectedAddress.getCep(), foundAddress.getCep());
        assertEquals(expectedAddress.getNumber(), foundAddress.getNumber());
        assertEquals(expectedAddress.getComplement(), foundAddress.getComplement());
        assertEquals(expectedAddress.getCity(), foundAddress.getCity());
        assertEquals(expectedAddress.getUf(), foundAddress.getUf());

        verify(personRepository, times(1)).findById(personId);
    }


    @Test(expected = EntityNotFoundException.class)
    public void testFindPersonByIdNotFound() {
        UUID personId = UUID.randomUUID();

        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        personService.getPersonById(personId);
    }

    @Test
    public void testUpdatePerson() {
        UUID personId = UUID.randomUUID();
        PersonDTO personDTO = new PersonDTO(personId, "henrique.villa", "12345678900", "123456789", "henrique.villa@example.com", Arrays.asList(new Address()));
        Person existingPerson = new Person(personId, "Carlos Oliveira", "98765432100", "567894123", "carlos.oliveira@example.com",  Arrays.asList(new Address()));

        when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).then(AdditionalAnswers.returnsFirstArg());

        Person updatedPerson = personService.updatePerson(personId, personDTO);

        assertNotNull(updatedPerson);
        assertEquals(personId, updatedPerson.getId());
        assertEquals(personDTO.name(), updatedPerson.getName());
        assertEquals(personDTO.cpf(), updatedPerson.getCpf());
        assertEquals(personDTO.rg(), updatedPerson.getRg());
        assertEquals(personDTO.email(), updatedPerson.getEmail());

        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdatePersonNotFound() {
        UUID personId = UUID.randomUUID();
        PersonDTO personDTO = new PersonDTO(personId, "Henrique villa", "12345678900", "123415625", "henrique.villa@example.com", Arrays.asList(new Address()));

        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        personService.updatePerson(personId, personDTO);
    }

    @Test
    public void testDeletePerson() {
        UUID personId = UUID.randomUUID();
        Person existingPerson = new Person(personId, "Henrique villa", "12345678900", "123456789", "henrique.villa@example.com", Arrays.asList(new Address()));

        when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));

        personService.deletePerson(personId);

        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).delete(existingPerson);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeletePersonNotFound() {
        UUID personId = UUID.randomUUID();

        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        personService.deletePerson(personId);
    }

}