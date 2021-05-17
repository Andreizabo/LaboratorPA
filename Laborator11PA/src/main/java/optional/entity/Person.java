package optional.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person
{
    @Id
    String name;

    @ManyToMany
    @JsonIgnore
    Set<Person> friends = new HashSet<>();

    @ManyToMany(mappedBy = "friends")
    @JsonIgnore
    Set<Person> friendsOf = new HashSet<>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Set<Person> getFriends()
    {
        return friends;
    }

    public void setFriends(Set<Person> friends)
    {
        this.friends = friends;
    }

    public Set<Person> getFriendsOf()
    {
        return friendsOf;
    }

    public void setFriendsOf(Set<Person> friendsOf)
    {
        this.friendsOf = friendsOf;
    }

    public Integer getFriendsNum()
    {
        return friends.size() + friendsOf.size();
    }
}