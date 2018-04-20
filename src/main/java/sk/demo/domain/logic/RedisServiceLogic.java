package sk.demo.domain.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.demo.common.logger.CommonLogger;
import sk.demo.storeLogic.RedisStoreLogic;

@Service
public class RedisServiceLogic {

	@Autowired
	CommonLogger commonLogger;
	
	@Autowired
	private RedisStoreLogic redisStoreLogic; 
	
	public void set(String key, Object value) {
		redisStoreLogic.set(key, value);
		commonLogger.commonRdsLog("IF-0001-XPG-MNGR", "1.0", "NEXTUI", "SET", "0000", "등록 성공");
    }
	
	public Object get(String key) {
		
		Object result = redisStoreLogic.get(key);
		commonLogger.commonRdsLog("IF-0002-XPG-MNGR", "1.0", "NEXTUI", "GET", "0000", "조회 성공");
        return result;
    }
}
