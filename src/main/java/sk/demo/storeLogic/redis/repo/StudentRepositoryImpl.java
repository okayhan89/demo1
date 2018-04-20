package sk.demo.storeLogic.redis.repo;

import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sk.demo.domain.dto.StudentDto;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private static final String KEY = "StudentDto";

    //@Autowired
    private RedisTemplate<String, Object> redisTemplate;
//    private HashOperations hashOperations;

   
    //@Autowired
    public StudentRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

//    @PostConstruct
//    private void init() {
//        hashOperations = redisTemplate.opsForHash();
//    }
//
    public void saveStudent(final StudentDto student) {
    	redisTemplate.opsForHash().put(this.KEY, student.getId(), student);
        //hashOperations.put(KEY, student.getId(), student);
    }

    public void updateStudent(final StudentDto student) {
    	redisTemplate.opsForHash().put(KEY, student.getId(), student);
    }

    public StudentDto findStudent(final String id) {
        return (StudentDto) redisTemplate.opsForHash().get(KEY, id);
    }

    public Map<Object, Object> findAllStudents() {
        return redisTemplate.opsForHash().entries(KEY);
    }

    public void deleteStudent(final String id) {
    	redisTemplate.opsForHash().delete(KEY, id);
    }
}
