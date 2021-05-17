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
    ResponseEntity<String> createFriends(@RequestBody Map<String, Long> friendsJson)
    {
        Long id1 = friendsJson.get("id1");
        Long id2 = friendsJson.get("id2");

        if (id1 == null || id2 == null)
            return ResponseEntity.badRequest().build();

        var personOpt1 = personRepository.findById(id1);
        var personOpt2 = personRepository.findById(id2);

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

    @GetMapping("/{id}")
    ResponseEntity<Iterable<Person>> listAllUserFriends(@PathVariable Long id)
    {
        var personOpt = personRepository.findById(id);
        if (personOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var person = personOpt.get();
        List<Person> result = new ArrayList<>();

        result.addAll(person.getFriends());

        result.addAll(person.getFriendsOf());

        return ResponseEntity.ok(result);
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
}
