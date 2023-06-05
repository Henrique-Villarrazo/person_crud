package br.com.villa.person.model;

import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "adress")
public class Address {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String street;
    private String district;
    private String cep;
    private String number;
    private String complement;
    private String city;
    private String uf;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Address(){}

    public Address(String street, String district, String cep, String number, String complement, String city, String uf) {
        this.street = street;
        this.district = district;
        this.cep = cep;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.uf = uf;
    }

    public String getStreet() {
        return street;
    }

    public String getDistrict() {
        return district;
    }

    public String getCep() {
        return cep;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getCity() {
        return city;
    }

    public String getUf() {
        return uf;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
