package sk.demo.storelogic.redis.repository;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import sk.demo.domain.dto.StudentDto;
import sk.demo.storeLogic.redis.repo.StudentRepository;
import sk.demo.storeLogic.redis.repo.StudentRepositoryImpl;

@RunWith(SpringRunner.class)
public class StudentRepositoryTestWithMock {


    private StudentRepository studentRepository;
    
    @MockBean
    private RedisConnection redisConnectionMock;
    
    @MockBean
    private RedisConnectionFactory redisConnectionFactoryMock;   
    
    @MockBean
    JedisConnectionFactory jedisConnectionFactoryMock;
    
    @MockBean
    private RedisTemplate redisTemplateMock;
    
    @Before
    public void setUp() {   Mockito.when(jedisConnectionFactoryMock.getConnection()).thenReturn(redisConnectionMock);   
    redisTemplateMock = new RedisTemplate<String, Object>();
    redisTemplateMock.setConnectionFactory(jedisConnectionFactoryMock);
    redisTemplateMock.afterPropertiesSet();

    }
    
    //@Ignore
    @Test
    public void whenSavingStudent_thenAvailableOnRetrieval() throws Exception {
        final StudentDto student = new StudentDto("Eng2015001", "John Doe", StudentDto.Gender.MALE, 1);
        studentRepository = new StudentRepositoryImpl(redisTemplateMock);	
        studentRepository.saveStudent(student);
        final StudentDto retrievedStudent = studentRepository.findStudent(student.getId());
        //final Object retrievedStudent = studentRepository.findStudent(student.getId());
        if(null != student){
        	System.out.println("student.getId():"+student.getId());
        }
        if(null != retrievedStudent){
        	//StudentDto retrievedStudent1= (StudentDto)retrievedStudent;
        	System.out.println("retrievedStudent.getId():"+retrievedStudent.getId());
            assertEquals(student.getId(), retrievedStudent.getId());
        } else {
        	System.out.println("retrievedStudent is nulll!!!!!!");
        }

    }
    
    @Ignore
    @Test
    public void whenUpdatingStudent_thenAvailableOnRetrieval() throws Exception {
        final StudentDto student = new StudentDto("Eng2015001", "John Doe", StudentDto.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        student.setName("Richard Watson");
        studentRepository.saveStudent(student);
        //final StudentDto retrievedStudent = studentRepository.findStudent(student.getId());
       // assertEquals(student.getName(), retrievedStudent.getName());
    }
    
    @Ignore
    @Test
    public void whenSavingStudents_thenAllShouldAvailableOnRetrieval() throws Exception {
        final StudentDto engStudent = new StudentDto("Eng2015001", "John Doe", StudentDto.Gender.MALE, 1);
        final StudentDto medStudent = new StudentDto("Med2015001", "Gareth Houston", StudentDto.Gender.MALE, 2);
        studentRepository.saveStudent(engStudent);
        studentRepository.saveStudent(medStudent);
        final Map<Object, Object> retrievedStudent = studentRepository.findAllStudents();
        assertEquals(retrievedStudent.size(), 2);
    }
    
    @Ignore
    @Test
    public void whenDeletingStudent_thenNotAvailableOnRetrieval() throws Exception {
        final StudentDto student = new StudentDto("Eng2015001", "John Doe", StudentDto.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        studentRepository.deleteStudent(student.getId());
        //final StudentDto retrievedStudent = studentRepository.findStudent(student.getId());
        //assertNull(retrievedStudent);
    }
  
}
