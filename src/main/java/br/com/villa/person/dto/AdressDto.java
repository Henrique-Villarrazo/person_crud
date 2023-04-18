package br.com.villa.person.dto;


import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record AdressDto(
        UUID id,
        @NotBlank
        String street,
        @NotBlank
        String district,
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep,
        @NotBlank
        String city,
        @NotBlank
        String uf,
        String complement,
        @NotBlank
        String number) {
}
