package br.com.villa.person.service;

import br.com.villa.person.dto.PersonDto;
import br.com.villa.person.model.Adress;
import br.com.villa.person.model.Person;
import br.com.villa.person.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    public PersonService(PersonRepository personRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    public PersonDto createPerson(PersonDto personDto) {
        Person person = convertToEntity(personDto);
        Person savedPerson = personRepository.save(person);
        return convertToDto(savedPerson);
    }

    public PersonDto getPersonById(UUID id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            return convertToDto(person);
        } else {
            throw new EntityNotFoundException("Person not found with id: " + id);
        }
    }

    public List<PersonDto> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        return persons.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PersonDto updatePerson(UUID id, PersonDto personDto) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setName(personDto.name());
            person.setCpf(personDto.cpf());
            person.setRg(personDto.rg());
            person.setEmail(personDto.email());
            person.setAdress((Adress) personDto.adressDto());
            Person updatedPerson = personRepository.save(person);
            return convertToDto(updatedPerson);
        } else {
            throw new EntityNotFoundException("Person not found with id: " + id);
        }
    }

    public void deletePerson(UUID id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            personRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Person not found with id: " + id);
        }
    }

    private PersonDto convertToDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

    private Person convertToEntity(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }
}
