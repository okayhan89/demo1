package sk.demo.storeLogic.redis.repo;

import java.util.Map;

import sk.demo.domain.dto.StudentDto;

public interface StudentRepository {

    void saveStudent(StudentDto studentDto);

    void updateStudent(StudentDto studentDto);

    StudentDto findStudent(String id);

    Map<Object, Object> findAllStudents();

    void deleteStudent(String id);
}
