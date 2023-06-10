package br.com.villa.person.controller;

import br.com.villa.person.dto.PersonDTO;
import br.com.villa.person.model.Address;
import br.com.villa.person.model.Person;
import br.com.villa.person.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PersonControllerTest {
    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void should_find_all_people() throws Exception {
        List<Person> peopleList = new ArrayList<>();

        when(personService.listAllPerson()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(peopleList.size()));
    }

    @Test
    public void should_find_by_id() throws Exception {
        UUID id = UUID.randomUUID();
        PersonDTO personDTO = new PersonDTO(id, "Henrique villa", "12345678900", "123456789", "henrique.villa@example.com", Arrays.asList(new Address()));
        when(personService.getPersonById(id)).thenReturn(personDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/person/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personDTO.id().toString()))
                .andExpect(jsonPath("$.name").value(personDTO.name()));
    }
    @Test
    void should_create_person() throws Exception {
        PersonDTO personDTO = new PersonDTO(
                UUID.randomUUID(),
                "Henrique Villa",
                "12345678900",
                "123456789",
                "henrique.villa@example.com",
                Arrays.asList(new Address()));
        when(personService.createPerson(any(PersonDTO.class))).thenReturn((Person) status().isCreated());

        var uri = new URI("/person");

        mockMvc.perform(post(uri)
                        .content(objectMapper.writeValueAsString(personDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }



    @Test
    void should_update_person() throws Exception {
        var id = UUID.randomUUID();
        PersonDTO personDTO = new PersonDTO(
            id,
                "Henrique Villa",
                "12345678900",
                "123456789",
                "henrique.villa@example.com",
                Arrays.asList(new Address()));
        when(personService.updatePerson(any(UUID.class), any(PersonDTO.class))).thenReturn((Person) status().isOk());

        var uri = new URI("/person/" + id);

        mockMvc.perform(put(uri)
                        .content(objectMapper.writeValueAsString(personDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void should_delete_person() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/person/{id}", id))
                .andExpect(status().isNoContent());
    }

}
