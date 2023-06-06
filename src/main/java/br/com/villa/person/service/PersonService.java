package br.com.villa.person.service;

import br.com.villa.person.dto.PersonDTO;
import br.com.villa.person.model.Address;
import br.com.villa.person.model.Person;
import br.com.villa.person.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(PersonDTO personDTO) {
        Person person = new Person.Builder()
                .id(personDTO.id())
                .name(personDTO.name())
                .cpf(personDTO.cpf())
                .rg(personDTO.rg())
                .email(personDTO.email())
                .address((List<Address>) personDTO.address())
                .build();
        return personRepository.save(person);
    }

    public List<PersonDTO> listAllPerson() {
        List<Person> people = personRepository.findAll();
        return people.stream().map(person -> new PersonDTO(
                person.getId(), person.getName(), person.getCpf(), person.getRg(), person.getEmail(), (List<Address>) person.getAddress()))
                .collect(Collectors.toList());
    }

    public Person findPersonById(UUID id) {
        Person person =  personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id " + id));
        return new Person(new PersonDTO(person.getId(), person.getName(), person.getCpf(), person.getRg(), person.getEmail(), (List<Address>) person.getAddress()));
    }

    public Person updatePerson(UUID id, PersonDTO personDTO) {
        Person person = personRepository.findById(personDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id " + id));
        person.setId(personDTO.id());
        person.setName(personDTO.name());
        person.setCpf(personDTO.cpf());
        person.setRg(personDTO.rg());
        person.setEmail(personDTO.email());
        person.setAddress((List<Address>) personDTO.address());
        person  = personRepository.save(person);
        return new Person(person.getId(), person.getName(), person.getCpf(), person.getRg(), person.getEmail(), person.getAddress());
    }

    public void deletePerson(UUID id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id " + id));
        personRepository.delete(person);
    }
}
