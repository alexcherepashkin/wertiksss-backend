package ua.alexch.demowert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repo) {
        this.repository = repo;
    }

    public List<Person> findAllByTerm(String term, String option) {
        if (term == null | option == null) {
            return null;
        }

        List<Person> persons = new ArrayList<>();

        if (option.equals(WertOptions.PHONE_NUMBER)) {
            persons = repository.findAllByPhoneNumber(term);
        }

        if (option.equals(WertOptions.LAST_NAME)) {
            persons = repository.findAllByLastName(term);
        }

        if (option.equals(WertOptions.PHONE_MODEL)) {
            persons = repository.findAllByPhoneModel(term);
        }

        return persons;
    }

    public Person findById(Long id) throws DomainException {
        return repository.findById(id).orElseThrow(() -> new DomainException("Failed to find person with ID=" + id));
    }

    public List<Person> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public Person savePerson(Person person) throws DomainException {
        if (person == null) {
            throw new DomainException("Person cannot be null");
        }
        return repository.saveAndFlush(person);
    }

    @Transactional
    public Person updatePerson(Person person) throws DomainException {
        if (person == null) {
            throw new DomainException("Person cannot be null");
        }

        if (!repository.existsById(person.getId())) {
            throw new DomainException("Failed to update person because he was not found");
        }
        return repository.saveAndFlush(person);
    }

    @Transactional
    public void removePerson(Long id) throws DomainException {
        if (id == null) {
            throw new DomainException("ID cannot be null");
        }
        repository.deleteById(id);
    }

}
