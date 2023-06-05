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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void should_find_all_person() throws Exception {
        List<Person> personList = new ArrayList<>();

        Mockito.when(personService.listAllPerson()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/person"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(personList.size()));
    }

    @Test
    public void should_find_by_id() throws Exception {
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "Henrique villa", "12345678900", "123456789", "henrique.villa@example.com", new Address());

        Mockito.when(personService.listPersonById(id)).thenReturn(person);

        mockMvc.perform(MockMvcRequestBuilders.get("/person/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(person.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(person.getName()));
    }

    @Test
    public void should_create_person() throws Exception {
        Person person = new Person();
        person.setName("Henrique villa");
        person.setCpf("12345678900");
        person.setRg("123456789");
        person.setEmail("henrique.villa@example.com");

        Address address = new Address();
        address.setCity("Caragua");
        address.setCep("12345");
        address.setDistrict("Distrito");
        address.setStreet("Street 1");
        address.setUf("SP");

        mockMvc.perform((RequestBuilder) post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(person))))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", is("Henrique villa")))
                .andExpect((ResultMatcher)jsonPath("$.cpf", is("12345678900")))
                .andExpect((ResultMatcher)jsonPath("$.rg", is("123456789")))
                .andExpect((ResultMatcher)jsonPath("$.email", is("henrique.villa@example.com")))
                .andExpect((ResultMatcher)jsonPath("$.address.street", is("12345")))
                .andExpect((ResultMatcher)jsonPath("$.address.city", is("Caragua")))
                .andExpect((ResultMatcher)jsonPath("$.address.cep", is("12345")))
                .andExpect((ResultMatcher)jsonPath("$.address.district", is("12345")))
                .andExpect((ResultMatcher)jsonPath("$.address.street", is("Street 1")))
                .andExpect((ResultMatcher)jsonPath("$.address.uf", is("SP")));
    }

    @Test
    public void should_update_person() throws Exception {
        Person person = new Person();
        person.setName("Henrique villa");
        person.setCpf("12345678900");
        person.setRg("123456789");
        person.setEmail("henrique.villa@example.com");

        Address address = new Address();
        address.setCity("Caragua");
        address.setCep("12345");
        address.setDistrict("Distrito");
        address.setStreet("Street 1");
        address.setUf("SP");

        mockMvc.perform((RequestBuilder) put("/api/persons/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(person))))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", is("Henrique villa")))
                .andExpect((ResultMatcher)jsonPath("$.cpf", is("12345678900")))
                .andExpect((ResultMatcher)jsonPath("$.rg", is("123456789")))
                .andExpect((ResultMatcher)jsonPath("$.email", is("henrique.villa@example.com")))
                .andExpect((ResultMatcher)jsonPath("$.address.street", is("12345")))
                .andExpect((ResultMatcher)jsonPath("$.address.city", is("Caragua")))
                .andExpect((ResultMatcher)jsonPath("$.address.cep", is("12345")))
                .andExpect((ResultMatcher)jsonPath("$.address.district", is("12345")))
                .andExpect((ResultMatcher)jsonPath("$.address.street", is("Street 1")))
                .andExpect((ResultMatcher)jsonPath("$.address.uf", is("SP")));
    }

    @Test
    public void should_delete_person() throws Exception {
        mockMvc.perform((RequestBuilder) delete("/api/persons/{id}", 1))
                .andExpect(status().isOk());
    }

}
