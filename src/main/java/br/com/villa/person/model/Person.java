package br.com.villa.person.model;

import br.com.villa.person.dto.PersonDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String name;
    private String cpf;
    private String rg;
    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Address> address;

    public Person() {
    }

    public Person(UUID id, String name, String cpf, String rg, String email, List<Address> address) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.email = email;
        this.address = address;
    }

    public Person(PersonDTO personDTO) {
        this.id = personDTO.id();
        this.name = personDTO.name();
        this.cpf = personDTO.cpf();
        this.rg = personDTO.rg();
        this.email = personDTO.email();


        List<Address> addresses = new ArrayList<>();
        for (Address addressDTO : personDTO.address()) {
            Address address = new Address(addressDTO.getStreet(), addressDTO.getCity(), addressDTO.getCep(), addressDTO.getNumber(), addressDTO.getComplement(),
                    addressDTO.getDistrict(), addressDTO.getUf());
            addresses.add(address);
        }
        this.address = addresses;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String cpf;
        private String rg;
        private String email;
        private List<Address> address;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder rg(String rg) {
            this.rg = rg;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder address(List<Address> address) {
            this.address = address;
            return this;
        }

        public Person build() {
            return new Person(id, name, cpf, rg, email, address);
        }
    }
}
