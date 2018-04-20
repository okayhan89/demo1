package sk.demo.storeLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonStoreLogic {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate; 
	
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}
	public Object get(String key){
		return redisTemplate.opsForValue().get(key);
	}
	public void registerUser(String key, Object hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

}
