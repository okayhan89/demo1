package sk.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

/**
 * Eureka API 기본 샘픔
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value= "/v1")
public class EurekaServiceController{

	private static final Logger logger = LoggerFactory.getLogger(EurekaServiceController.class.getName());
	
	// RestTemplate
	@Autowired
	private RestTemplate restTemplate;
		
	// RestTemplate + Ribbon
	@Autowired
	@LoadBalanced
	private RestTemplate loadBalanced;
	
	@Autowired
	private EurekaClient discoveryClient;
	
	@RequestMapping(value= "/eureka", method=RequestMethod.GET)
	public Map<String, Object> main(HttpServletRequest request, HttpServletResponse response/*, @PathVariable String ver*/) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> node = new HashMap<String, Object>();
		// Ribbon 을 이용한 호출
		ResponseEntity<String> entity1 = this.loadBalanced.getForEntity("http://demo/v1/rest?method=get", String.class);
		logger.debug("Ribbon : " + entity1.getBody());
		
		// Eureka만을 이용한 호출
		InstanceInfo instance = discoveryClient.getNextServerFromEureka("demo", false);
		ResponseEntity<String> entity2 = this.restTemplate.getForEntity(instance.getHomePageUrl() + "/v1/rest?method=get", String.class);
	    logger.debug("Eureka : " + instance.getHomePageUrl());
	    logger.debug("Eureka : " + entity2.getBody());
		
		node.put("Node", "Value");
		result.put("Result", node);
		
		return result;
	}
			
}
