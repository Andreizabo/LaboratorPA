package optional.controller;

import optional.entity.Person;
import optional.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController
{
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/")
    ResponseEntity<Person> createPerson(@RequestBody Person person)
    {
        if (person.getId() != null && personRepository.findById(person.getId()).isPresent())
        {
            return ResponseEntity.badRequest().build();
        }

        Person createdPerson = personRepository.save(person);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdPerson.getId()).toUri();
        return ResponseEntity.created(uri).body(createdPerson);
    }

    @GetMapping("/")
    ResponseEntity<Iterable<Person>> listAllPersons()
    {
        return ResponseEntity.ok(personRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Person> listPerson(@PathVariable Long id)
    {
        Optional<Person> personOpt = personRepository.findById(id);
        if (personOpt.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personOpt.get());
    }

    @PutMapping("/{id}")
    ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person)
    {
        if (person.getId() != null && !id.equals(person.getId()))
        {
            return ResponseEntity.badRequest().build();
        }

        if (person.getId() == null)
        {
            person.setId(id);
        }

        Person updatedPerson = personRepository.save(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Person> deletePerson(@PathVariable Long id)
    {
        if (personRepository.findById(id).isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        personRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
