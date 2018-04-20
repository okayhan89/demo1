package sk.demo.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.jsonpath.JsonPath;

import sk.demo.common.exception.EnumExceptionMessage;
import sk.demo.domain.dto.PersonDto;
import sk.demo.domain.service.PersonService;

@RestController
@RequestMapping(value = "/v1")
public class PersonController {
	
    private final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    // =========================================== Get All Users ==========================================

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public ResponseEntity<List<PersonDto>> getAll() {
        LOG.info("getting all persons");
        List<PersonDto> persons = personService.getAll();

        if (persons == null || persons.isEmpty()){
            LOG.info("no persons found");
            return new ResponseEntity<List<PersonDto>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<PersonDto>>(persons, HttpStatus.OK);
    }

    // =========================================== Get PersonDto By ID =========================================

    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public ResponseEntity<PersonDto> get(@PathVariable("id") int id){
        LOG.info("getting person with id: {}", id);
        PersonDto person = personService.findById(id);

        if (person == null){
            LOG.info("person with id {} not found", id);
            return new ResponseEntity<PersonDto>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<PersonDto>(person, HttpStatus.OK);
    }

    // =========================================== Create New PersonDto ========================================

    @RequestMapping(value = "/person", params = "method=post", method = RequestMethod.POST)
    public Map<String, Object> create(@RequestBody String payload, HttpServletRequest request,
			HttpServletResponse response){

		Map<String, Object> result = new HashMap<String, Object>();
		
		String id = JsonPath.parse(payload).read("$.id");
		String name = JsonPath.parse(payload).read("$.name");

		result.put("id", id);
		result.put("name", name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
    }

    // =========================================== Update Existing PersonDto ===================================

    @RequestMapping(value = "/person/{id}", params = "method=put", method = RequestMethod.PUT)
    public ResponseEntity<PersonDto> update(@PathVariable int id, @RequestBody PersonDto person){
        LOG.info("updating person: {}", person);
        PersonDto currentUser = personService.findById(id);

        if (currentUser == null){
            LOG.info("PersonDto with id {} not found", id);
            return new ResponseEntity<PersonDto>(HttpStatus.NOT_FOUND);
        }

        currentUser.setId(person.getId());
        currentUser.setUsername(person.getUsername());

        personService.update(person);
        return new ResponseEntity<PersonDto>(currentUser, HttpStatus.OK);
    }

    // =========================================== Delete PersonDto ============================================

    @RequestMapping(value = "/person/{id}", params = "method=delete", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") int id){
        LOG.info("deleting person with id: {}", id);
        PersonDto person = personService.findById(id);

        if (person == null){
            LOG.info("Unable to delete. PersonDto with id {} not found", id);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        personService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

