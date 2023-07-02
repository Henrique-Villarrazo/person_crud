package br.com.villa.person.service;

import br.com.villa.person.dto.PersonDTO;
import br.com.villa.person.model.Address;
import br.com.villa.person.model.Person;
import br.com.villa.person.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(PersonDTO personDTO) {
         Set<Address> addresses =  personDTO.address().stream()
                .map(addressDTO -> new Address(
                        addressDTO.getStreet(),
                        addressDTO.getDistrict(),
                        addressDTO.getCep(),
                        addressDTO.getNumber(),
                        addressDTO.getComplement(),
                        addressDTO.getCity(),
                        addressDTO.getUf(),
                        addressDTO.getPerson())).collect(Collectors.toSet());

        Person person = new Person.Builder()
                .id(personDTO.id())
                .name(personDTO.name())
                .cpf(personDTO.cpf())
                .rg(personDTO.rg())
                .email(personDTO.email())
                .address(personDTO.address())
                .build();

        Person savedPerson = personRepository.save(person);

        for (Address address : savedPerson.getAddress()) {
            address.setPerson(savedPerson);
        }

        personRepository.save(savedPerson);

        return savedPerson;
    }


    public Person updatePerson(UUID id, PersonDTO personDTO) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id " + id));

        person.setName(personDTO.name());
        person.setCpf(personDTO.cpf());
        person.setRg(personDTO.rg());
        person.setEmail(personDTO.email());

        Set<Address> addresses = personDTO.address().stream()
                .map(addressDTO -> new Address(
                        addressDTO.getStreet(),
                        addressDTO.getCity(),
                        addressDTO.getCep(),
                        addressDTO.getNumber(),
                        addressDTO.getComplement(),
                        addressDTO.getDistrict(),
                        addressDTO.getUf(),
                        addressDTO.getPerson()))
                .collect(Collectors.toSet());

        person.setAddress(addresses);

        return personRepository.save(person);
    }


    public Set<PersonDTO> listAllPeople() {
        List<Person> people = personRepository.findAll();
        return  people.stream()
                .map(person -> new PersonDTO(
                        person.getId(),
                        person.getName(),
                        person.getCpf(),
                        person.getRg(),
                        person.getEmail(),
                        mapAddressSet(person.getAddress())))
                .collect(Collectors.toSet());
    }

    private Set<Address> mapAddressSet(Set<Address> addresses) {
        return  addresses.stream()
                .map(address -> new Address(
                        address.getStreet(),
                        address.getDistrict(),
                        address.getCep(),
                        address.getNumber(),
                        address.getComplement(),
                        address.getCity(),
                        address.getUf(),
                        address.getPerson()))
                .collect(Collectors.toSet());
    }


    public PersonDTO getPersonById(UUID id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id " + id));

        Set<Address> addressDTOs = mapAddressSet(person.getAddress());

        Set<Address> addresses =  addressDTOs.stream()
                .map(addressDTO -> new Address(
                        addressDTO.getStreet(),
                        addressDTO.getDistrict(),
                        addressDTO.getCep(),
                        addressDTO.getNumber(),
                        addressDTO.getComplement(),
                        addressDTO.getCity(),
                        addressDTO.getUf(),
                        addressDTO.getPerson()))
                .collect(Collectors.toSet());

        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getCpf(),
                person.getRg(),
                person.getEmail(),
                addresses);
    }


    public void deletePerson(UUID id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id " + id));
        personRepository.delete(person);
    }

}
