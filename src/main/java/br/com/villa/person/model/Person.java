package br.com.villa.person.model;

import br.com.villa.person.dto.PersonDTO;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private  UUID id;

    private String name;

    private String cpf;

    private String rg;

    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Address> address;

    public Person(UUID id, String name, String cpf, String rg, String email, Address address) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.email = email;
        this.address = (List<Address>) address;
    }

    public Person(PersonDTO personDTO) {}

    public Person() {

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRg() {
        return rg;
    }

    public String getEmail() {
        return email;
    }

    public Address getAdress() {return (Address) address;}

    public void setAdress(Address address) {
        this.address = (List<Address>) address;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String cpf;
        private String rg;
        private String email;
        private Address address;

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

        public Builder address(Address address) {
            this.address = this.address;
            return this;
        }

        public Person build() {
            PersonDTO personDTO = new PersonDTO(id, name, cpf, rg, email, address);
            Person person = new Person(personDTO);
            return person;
        }
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Address> getAddress() {
        return address;
    }


}
