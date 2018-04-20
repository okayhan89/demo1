package sk.demo.domain.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import sk.demo.domain.dto.PersonDto;

@Service
public class PersonService {


    static final AtomicInteger counter = new AtomicInteger();
    static List<PersonDto> users = new ArrayList<PersonDto>(
            Arrays.asList(
                    new PersonDto(counter.incrementAndGet(), "Daenerys Targaryen"),
                    new PersonDto(counter.incrementAndGet(), "John Snow"),
                    new PersonDto(counter.incrementAndGet(), "Arya Stark"),
                    new PersonDto(counter.incrementAndGet(), "Cersei Baratheon")));


    public List<PersonDto> getAll() {
        return users;
    }


    public PersonDto findById(int id) {
        for (PersonDto user : users){
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }


    public PersonDto findByName(String name) {
        for (PersonDto user : users){
            if (user.getUsername().equals(name)){
                return user;
            }
        }
        return null;
    }


    public void create(PersonDto user) {
        user.setId(counter.incrementAndGet());
        users.add(user);
    }


    public void update(PersonDto user) {
        int index = users.indexOf(user);
        users.set(index, user);
    }


    public void delete(int id) {
        PersonDto user = findById(id);
        users.remove(user);
    }


    public boolean exists(PersonDto user) {
        return findByName(user.getUsername()) != null;
    }
    
}
