package br.com.villa.person.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;
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

        Set<br.com.villa.person.model.Address> address) {


}
