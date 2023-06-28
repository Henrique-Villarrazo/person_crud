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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonDTO> findAllPeople() {
        return personService.listAllPerson();
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
                address.getNumber()
        );
    }

    private List<AddressDTO> convertToAddressDTOList(List<Address> addresses) {
        List<AddressDTO> addressDTOs = new ArrayList<>();

        for (Address address : addresses) {
            AddressDTO addressDTO = new AddressDTO(
                    address.getId(),
                    address.getStreet(),
                    address.getDistrict(),
                    address.getCep(),
                    address.getCity(),
                    address.getUf(),
                    address.getComplement(),
                    address.getNumber()
            );
            addressDTOs.add(addressDTO);
        }

        return addressDTOs;
    }

    private PersonDTO mapToDTO(Person person) {
        List<AddressDTO> addressDTOs = convertToAddressDTOList(person.getAddress());
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getCpf(),
                person.getRg(),
                person.getEmail(),
                person.getAddress()
        );
    }

}
