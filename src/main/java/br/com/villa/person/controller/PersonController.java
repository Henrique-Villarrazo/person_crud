package br.com.villa.person.controller;

import br.com.villa.person.dto.PersonDto;
import br.com.villa.person.service.PersonService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ModelAndView listPersons() {
        List<PersonDto> persons = personService.getAllPersons();
        ModelAndView modelAndView = new ModelAndView("persons");
        modelAndView.addObject("persons", persons);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showPerson(@PathVariable UUID id) {
        PersonDto person = personService.getPersonById(id);
        ModelAndView modelAndView = new ModelAndView("person");
        modelAndView.addObject("person", person);
        return modelAndView;
    }


    @PostMapping
    public String createPerson(@ModelAttribute("person") PersonDto personDto) {
        personService.createPerson(personDto);
        return "redirect:/persons";
    }

    @PutMapping("/{id}")
    public String updatePerson(@PathVariable UUID id, @ModelAttribute("person") PersonDto personDto) {
        personService.updatePerson(id, personDto);
        return "redirect:/persons";
    }

    @DeleteMapping("/{id}/delete")
    public String deletePerson(@PathVariable UUID id) {
        personService.deletePerson(id);
        return "redirect:/persons";
    }
}