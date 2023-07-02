package br.com.villa.person.controller;

import br.com.villa.person.dto.AddressDTO;
import br.com.villa.person.dto.PersonDTO;
import br.com.villa.person.model.Address;
import br.com.villa.person.model.Person;
import br.com.villa.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Set<PersonDTO> findAllPeople() {
        return personService.listAllPeople();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable UUID id) {
        PersonDTO personDTO = personService.getPersonById(id);
        return ResponseEntity.ok(personDTO);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDTO) {
        Person createdPerson = personService.createPerson(personDTO);
        PersonDTO createdPersonDTO = mapToDTO(createdPerson);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPerson.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdPersonDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable UUID id, @RequestBody PersonDTO personDTO) {
        Person updatedPerson = personService.updatePerson(id, personDTO);
        PersonDTO updatedPersonDTO = mapToDTO(updatedPerson);
        return ResponseEntity.ok(updatedPersonDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    private AddressDTO convertToAddressDTO(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getDistrict(),
                address.getCep(),
                address.getCity(),
                address.getUf(),
                address.getComplement(),
                address.getNumber(),
                address.getPerson()
        );
    }

    private Set<AddressDTO> convertToAddressDTOSet(Set<Address> addresses) {
        Set<AddressDTO> addressDTOs = new HashSet<AddressDTO>();

        for (Address address : addresses) {
            AddressDTO addressDTO = new AddressDTO(
                    address.getId(),
                    address.getStreet(),
                    address.getDistrict(),
                    address.getCep(),
                    address.getCity(),
                    address.getUf(),
                    address.getComplement(),
                    address.getNumber(),
                    address.getPerson()
            );
            addressDTOs.add(addressDTO);
        }

        return addressDTOs;
    }

    private PersonDTO mapToDTO(Person person) {
        Set<Address> addresses = new HashSet<>(person.getAddress());

        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getCpf(),
                person.getRg(),
                person.getEmail(),
                addresses
        );
    }
}
