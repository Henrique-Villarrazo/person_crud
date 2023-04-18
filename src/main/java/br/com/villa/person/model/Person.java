package br.com.villa.person.model;

import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private UUID id;

    private String name;

    private String cpf;

    private String rg;

    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private Adress adress;

    public Person(UUID id, String name, String cpf, String rg, String email, Adress adress) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.email = email;
        this.adress = adress;
    }

    public Person() {}

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

    public Adress getAdress() {return (Adress) adress;}

    public void setAdress(Adress adress) {
        this.adress = adress;
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
        private Adress adress;

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

        public Builder adress(Adress adress) {
            this.adress = this.adress;
            return this;
        }

        public Person build() {
            Person person = new Person();
            person.setId(id);
            person.setName(name);
            person.setCpf(cpf);
            person.setRg(rg);
            person.setEmail(email);
            person.setAdress(adress);
            return person;
        }
    }
}
