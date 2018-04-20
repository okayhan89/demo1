package sk.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sk.demo.domain.logic.RedisServiceLogic;

/**
 * Redis 샘픔
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value= "/v1")
public class RedisServiceController{

	private static final Logger logger = LoggerFactory.getLogger(RedisServiceController.class.getName());
	
	@Autowired
	private RedisServiceLogic redisServiceLogic;
	
	@RequestMapping(value= "/redis", method=RequestMethod.GET)
	public Map<String, Object> main(HttpServletRequest request, HttpServletResponse response/*, @PathVariable String ver*/) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> node = new HashMap<String, Object>();
		redisServiceLogic.set("test", "test date");
		
		node.put("Node", "Value");
		result.put("Result", node);
		
		return result;
	}
}
