package br.com.villa.person.dto;

import br.com.villa.person.model.Address;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

public record PersonDTO(
        UUID id,

        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
        String cpf,

        @NotBlank
        @Pattern(regexp = "\\d{9}")
        String rg,

        @Email
        String email,

        @NotBlank
        List<Address> address) {

}
