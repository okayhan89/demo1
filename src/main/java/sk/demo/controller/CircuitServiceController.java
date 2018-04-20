package sk.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import sk.demo.common.exception.EnumExceptionMessage;

/**
 * Resp API 기본 샘픔
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/v1")
public class CircuitServiceController {

	private static final Logger logger = LoggerFactory.getLogger(CircuitServiceController.class.getName());

	// GET
	@HystrixCommand(commandKey="IF-DEMO-001",
			fallbackMethod="getFallbackForDemoCircuit",
			ignoreExceptions = {RuntimeException.class})
	@RequestMapping(value = "/circuit", params = "method=get", method = RequestMethod.GET)
	public Map<String, Object> get(HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		String api_if = request.getParameter("if");
		String ver = request.getParameter("ver");
		String ui_name = request.getParameter("ui_name");
		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());
		
		try {
			Thread.sleep(4000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}

	
	@HystrixCommand(commandKey="IF-DEMO-002",
			fallbackMethod="getFallbackForDemoCircuit")
	@RequestMapping(value = "/circuit1", params = "method=get", method = RequestMethod.GET)
	public Map<String, Object> get1(HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		String api_if = request.getParameter("if");
		String ver = request.getParameter("ver");
		String ui_name = request.getParameter("ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());
		
		try {
			Thread.sleep(4000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	/*
	 *  IF-DEMO-001  Demo Fallback 함수
	 */
	public Map<String, Object> getFallbackForDemoCircuit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		String api_if = request.getParameter("if");
		String ver = request.getParameter("ver");
		String ui_name = request.getParameter("ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", "Circuit Open");
		
		return result;
	}

}
