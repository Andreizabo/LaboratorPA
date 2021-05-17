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
        if (person.getName() != null && personRepository.findById(person.getName()).isPresent())
        {
            return ResponseEntity.badRequest().build();
        }

        Person createdPerson = personRepository.save(person);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(createdPerson.getName()).toUri();
        return ResponseEntity.created(uri).body(createdPerson);
    }

    @GetMapping("/")
    ResponseEntity<Iterable<Person>> listAllPersons()
    {
        return ResponseEntity.ok(personRepository.findAll());
    }

    @GetMapping("/{name}")
    ResponseEntity<Person> listPerson(@PathVariable String name)
    {
        Optional<Person> personOpt = personRepository.findById(name);
        if (personOpt.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personOpt.get());
    }

    @PutMapping("/{name}")
    ResponseEntity<Person> updatePerson(@PathVariable String name, @RequestBody Person person)
    {
        if (person.getName() != null && !name.equals(person.getName()))
        {
            return ResponseEntity.badRequest().build();
        }

        if (person.getName() == null)
        {
            person.setName(name);
        }

        Person updatedPerson = personRepository.save(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{name}")
    ResponseEntity<Person> deletePerson(@PathVariable String name)
    {
        var personOpt = personRepository.findById(name);
        if (personOpt.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        var person = personOpt.get();

        for (var friend : person.getFriends())
            friend.getFriendsOf().remove(person);

        for (var friend : person.getFriendsOf())
            friend.getFriends().remove(person);

        personRepository.deleteById(name);

        return ResponseEntity.noContent().build();
    }
}
