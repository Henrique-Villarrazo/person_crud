package br.com.villa.person.controller;

import br.com.villa.person.dto.PersonDTO;
import br.com.villa.person.model.Person;
import br.com.villa.person.service.PersonService;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonDTO> findAllPerson() {
        return personService.listAllPerson();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable UUID id) {
        Person person = personService.listPersonById(id);
        return ResponseEntity.ok(new PersonDTO(person.getId(), person.getName(), person.getCpf(), person.getRg(), person.getEmail(), person.getAdress()));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDTO) {
        Person person = personService.createPerson(personDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(location).body(new PersonDTO(person.getId(), person.getName(), person.getCpf(), person.getRg(), person.getEmail(), person.getAdress()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable UUID id, @RequestBody PersonDTO personDTO) {
        Person person = personService.updatePerson(id, personDTO);
        return ResponseEntity.ok(new PersonDTO(person.getId(), person.getName(), person.getCpf(), person.getRg(), person.getEmail(), person.getAdress()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonDTO> delete(@PathVariable UUID id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

}