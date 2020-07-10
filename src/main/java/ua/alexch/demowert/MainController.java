package ua.alexch.demowert;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
//@CrossOrigin(allowedHeaders = { "*" }, origins = { "*", "http://localhost:4200", "http://localhost:8080" })
public class MainController {
    private final PersonService service;

    @Autowired
    public MainController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public String hello() {
        return "Hello AlexCH!!";
    }

    @GetMapping("/persons/search")
    public List<Person> findAllByTerm(@RequestParam(name = "term", required = false) String term,
            @RequestParam(name = "option", required = false) String option) {
        return service.findAllByTerm(term, option);
    }

    @GetMapping("/persons/{id}")
    public Person findById(@PathVariable("id") Long id) throws DomainException {
        return service.findById(id);
    }

    @GetMapping("/persons")
    public List<Person> findAll() {
        return service.findAll();
    }

    @PostMapping("/persons")
    public Person savePerson(@RequestBody Person person) throws DomainException {
        return service.savePerson(person);
    }

    @PutMapping("/persons/{id}")
    public Person updatePerson(@RequestBody Person person) throws DomainException {
        return service.updatePerson(person);
    }

    @DeleteMapping("/persons/{id}")
    public void removePerson(@PathVariable("id") Long id) throws DomainException {
        service.removePerson(id);
    }

}
