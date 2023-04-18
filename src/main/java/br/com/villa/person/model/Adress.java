package br.com.villa.person.model;

import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "adress")
public class Adress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Adress(String street, String district, String cep, String number, String complement, String city, String uf) {
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
}
