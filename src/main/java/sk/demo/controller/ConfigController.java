package sk.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Config Server 샘픔
 * @author Administrator
 *
 */
@RefreshScope
@RestController
@RequestMapping(value= "/v1")
public class ConfigController{

	private static final Logger logger = LoggerFactory.getLogger(ConfigController.class.getName());
	
	@Value("${greeting}")
	String greeting="default";
	
	
	@RequestMapping(value= "/config", method=RequestMethod.GET)
	public Map<String, Object> main(HttpServletRequest request, HttpServletResponse response/*, @PathVariable String ver*/) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> node = new HashMap<String, Object>();
		
		node.put("Node", "Value");
		node.put("greeting", this.greeting);
		result.put("Result", node);
		
		return result;
	}
	
}
