package sk.demo.storelogic.redis.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import sk.demo.domain.dto.StudentDto;
import sk.demo.storeLogic.redis.repo.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@AutoConfigureMockMvc
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void whenSavingStudent_thenAvailableOnRetrieval() throws Exception {
        final StudentDto student = new StudentDto("Eng2015001", "John Doe", StudentDto.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        final StudentDto retrievedStudent = studentRepository.findStudent(student.getId());
        assertEquals(student.getId(), retrievedStudent.getId());
    }

    @Test
    public void whenUpdatingStudent_thenAvailableOnRetrieval() throws Exception {
        final StudentDto student = new StudentDto("Eng2015001", "John Doe", StudentDto.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        student.setName("Richard Watson");
        studentRepository.saveStudent(student);
        final StudentDto retrievedStudent = studentRepository.findStudent(student.getId());
        assertEquals(student.getName(), retrievedStudent.getName());
    }

    @Test
    public void whenSavingStudents_thenAllShouldAvailableOnRetrieval() throws Exception {
        final StudentDto engStudent = new StudentDto("Eng2015001", "John Doe", StudentDto.Gender.MALE, 1);
        final StudentDto medStudent = new StudentDto("Med2015001", "Gareth Houston", StudentDto.Gender.MALE, 2);
        studentRepository.saveStudent(engStudent);
        studentRepository.saveStudent(medStudent);
        final Map<Object, Object> retrievedStudent = studentRepository.findAllStudents();
        assertEquals(retrievedStudent.size(), 2);
    }

    @Test
    public void whenDeletingStudent_thenNotAvailableOnRetrieval() throws Exception {
        final StudentDto student = new StudentDto("Eng2015001", "John Doe", StudentDto.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        studentRepository.deleteStudent(student.getId());
        final StudentDto retrievedStudent = studentRepository.findStudent(student.getId());
        assertNull(retrievedStudent);
    }
}
