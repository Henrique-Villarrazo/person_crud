package br.com.villa.person.dto;


import br.com.villa.person.model.Address;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record PersonDTO(
        @JsonProperty
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

        @NotNull
        Address address)  { }
