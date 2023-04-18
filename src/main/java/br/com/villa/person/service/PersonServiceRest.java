package br.com.villa.person.service;/*package com.example.demo.service;

import com.example.demo.dto.PersonDto;
import com.example.demo.model.Adress;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceRest {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceRest(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(PersonDto personDto) {
        Person person = new Person.Builder()
                .id(personDto.id())
                .name(personDto.name())
                .cpf(personDto.cpf())
                .rg(personDto.rg())
                .email(personDto.email())
                .adress(personDto.adress())
                .build();
        return personRepository.save(person);
    }

    public List<PersonDto> listAllPerson() {
        List<Person> people = personRepository.findAll();
        return people.stream().map(person -> new PersonDto(
                person.getId(), person.getName(), person.getCpf(), person.getRg(), person.getEmail(), person.getAdress()))
                .collect(Collectors.toList());
    }

    public Person listPersonById(UUID id) {
        Person person =  personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com id " + id));
        return new Person(new PersonDto(person.getId(), person.getName(), person.getCpf(), person.getRg(), person.getEmail(), person.getAdress()));
    }

    public Person updatePerson(UUID id, PersonDto personDto) {
        Person person = personRepository.findById(personDto.id())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com id " + id));
        person.setId(personDto.id());
        person.setName(personDto.name());
        person.setCpf(personDto.cpf());
        person.setRg(personDto.rg());
        person.setEmail(personDto.email());
        person.setAdress((List<Adress>) personDto.adress());
        person  = personRepository.save(person);
        return new Person(person.getId(), person.getName(), person.getCpf(), person.getRg(), person.getEmail(), person.getAdress());
    }

    public void deletePerson(UUID id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com id " + id));
        personRepository.delete(person);
    }
}
 */