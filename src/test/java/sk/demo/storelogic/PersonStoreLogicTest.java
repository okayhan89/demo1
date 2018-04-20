package sk.demo.storelogic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import sk.demo.storeLogic.PersonStoreLogic;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMockMvc

public class PersonStoreLogicTest {
	
	@Autowired
	PersonStoreLogic personStoreLogic;
	

    String hashKey;
    String id;
    String name;

    @Before
    public void init() {
    	hashKey = "sk:demo:person";
    	id = "demotest";
        name ="김유신";
    }
    
    @Test
    public void registerUser() {    
    	personStoreLogic.registerUser(hashKey, id, name);
    }

    
}
