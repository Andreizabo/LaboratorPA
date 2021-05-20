package optional.controller;

import optional.entity.Person;
import optional.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("friends")
public class FriendsController
{
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/")
    ResponseEntity<String> createFriends(@RequestBody Map<String, String> friendsJson)
    {
        String name1 = friendsJson.get("name1");
        String name2 = friendsJson.get("name2");

        if (name1 == null || name2 == null)
            return ResponseEntity.badRequest().build();

        var personOpt1 = personRepository.findById(name1);
        var personOpt2 = personRepository.findById(name2);

        if (personOpt1.isEmpty() || personOpt2.isEmpty())
            return ResponseEntity.notFound().build();

        var person1 = personOpt1.get();
        var person2 = personOpt2.get();

        person1.getFriends().add(person2);
        person2.getFriendsOf().add(person1);

        personRepository.save(person1);
        personRepository.save(person2);

        return ResponseEntity.ok("Friendship created");
    }

    @GetMapping("/{name}")
    ResponseEntity<Iterable<Person>> listAllUserFriends(@PathVariable String name)
    {
        var personOpt = personRepository.findById(name);
        if (personOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var person = personOpt.get();
        List<Person> result = new ArrayList<>();

        result.addAll(person.getFriends());

        result.addAll(person.getFriendsOf());

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{name1}/{name2}")
    ResponseEntity<String> deletePerson(@PathVariable String name1, @PathVariable String name2)
    {
        var personOpt1 = personRepository.findById(name1);
        var personOpt2 = personRepository.findById(name2);

        if (personOpt1.isEmpty() || personOpt2.isEmpty())
            return ResponseEntity.notFound().build();

        var person1 = personOpt1.get();
        var person2 = personOpt2.get();

        if(!person1.getFriends().contains(person2)) {
            return ResponseEntity.notFound().build();
        }

        person1.getFriends().remove(person2);
        person2.getFriendsOf().remove(person1);

        personRepository.save(person1);
        personRepository.save(person2);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/least/{k}")
    ResponseEntity<Iterable<Person>> listLeastConnectedUsers(@PathVariable Integer k)
    {
        List<Person> allPersons = getPersonsSorted(k);
        if (allPersons == null)
            return ResponseEntity.badRequest().build();

        List<Person> result = new ArrayList<>();
        for (int i=0;i<k;i++)
            result.add(allPersons.get(i));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/most/{k}")
    ResponseEntity<Iterable<Person>> listMostConnectedUsers(@PathVariable Integer k)
    {
        List<Person> allPersons = getPersonsSorted(k);
        if (allPersons == null)
            return ResponseEntity.badRequest().build();

        List<Person> result = new ArrayList<>();
        for (int i=0;i<k;i++)
            result.add(allPersons.get(allPersons.size()-i-1));

        return ResponseEntity.ok(result);
    }

    List<Person> getPersonsSorted(int k)
    {
        List<Person> allPersons = new ArrayList<>();
        personRepository.findAll().forEach(allPersons::add);

        if (k > allPersons.size())
            return null;

        allPersons.sort(Comparator.comparingInt(Person::getFriendsNum));
        return allPersons;
    }

//    @GetMapping("/important")
//    ResponseEntity<Iterable<Person>> listImportantUsers()
//    {
//
//    }
}
