package br.com.villa.person.dto;


import java.util.List;
import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record PersonDto(
        UUID id,

        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "")
        String cpf,

        @NotBlank
        @Pattern(regexp = "")
        String rg,

        @Email
        String email,

        @NotNull
        List<AdressDto> adressDto)  { }
