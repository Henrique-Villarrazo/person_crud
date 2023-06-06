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
        List<Address> addresses = personDTO.address().stream()
                .map(addressDTO -> new Address(
                        addressDTO.getStreet(),
                        addressDTO.getDistrict(),
                        addressDTO.getCep(),
                        addressDTO.getNumber(),
                        addressDTO.getComplement(),
                        addressDTO.getCity(),
                        addressDTO.getUf()))
                .collect(Collectors.toList());

        Person person = new Person.Builder()
                .id(personDTO.id())
                .name(personDTO.name())
                .cpf(personDTO.cpf())
                .rg(personDTO.rg())
                .email(personDTO.email())
                .address(addresses)
                .build();

        return personRepository.save(person);
    }

    public Person updatePerson(UUID id, PersonDTO personDTO) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id " + id));

        person.setName(personDTO.name());
        person.setCpf(personDTO.cpf());
        person.setRg(personDTO.rg());
        person.setEmail(personDTO.email());

        List<Address> addresses = personDTO.address().stream()
                .map(addressDTO -> new Address(
                        addressDTO.getStreet(),
                        addressDTO.getDistrict(),
                        addressDTO.getCep(),
                        addressDTO.getNumber(),
                        addressDTO.getComplement(),
                        addressDTO.getCity(),
                        addressDTO.getUf()))
                .collect(Collectors.toList());

        person.setAddress(addresses);

        return personRepository.save(person);
    }


    public List<PersonDTO> listAllPerson() {
        List<Person> people = personRepository.findAll();
        return people.stream()
                .map(person -> new PersonDTO(
                        person.getId(),
                        person.getName(),
                        person.getCpf(),
                        person.getRg(),
                        person.getEmail(),
                        mapAddressList(person.getAddress())))
                .collect(Collectors.toList());
    }

    private List<Address> mapAddressList(List<Address> addresses) {
        return addresses.stream()
                .map(address -> new Address(
                        address.getStreet(),
                        address.getDistrict(),
                        address.getCep(),
                        address.getNumber(),
                        address.getComplement(),
                        address.getCity(),
                        address.getUf()))
                .collect(Collectors.toList());
    }


    public PersonDTO getPersonById(UUID id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id " + id));

        List<Address> addressDTOs = mapAddressList(person.getAddress());

        List<Address> addresses = addressDTOs.stream()
                .map(addressDTO -> new Address(
                        addressDTO.getStreet(),
                        addressDTO.getCity(),
                        addressDTO.getCep(),
                        addressDTO.getNumber(),
                        addressDTO.getComplement(),
                        addressDTO.getDistrict(),
                        addressDTO.getUf()))
                .collect(Collectors.toList());

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
