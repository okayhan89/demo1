package sk.demo.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.jsonpath.JsonPath;

import sk.demo.common.exception.EnumExceptionMessage;

/**
 * Resp API 기본 샘픔
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/v1")
public class RestServiceController {

	private static final Logger logger = LoggerFactory.getLogger(RestServiceController.class.getName());

	@Value("${test.appid:local}")
	String applicationid;

	// GET
	@RequestMapping(value = "/rest", params = "method=get", method = RequestMethod.GET)
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

		return result;
	}

	// POST - Get
	@RequestMapping(value = "/rest", params = "method=get", method = RequestMethod.POST)
	public Map<String, Object> post_get(@RequestBody String payload, HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new HashMap<String, Object>();

		String api_if = JsonPath.parse(payload).read("$.if");
		String ver = JsonPath.parse(payload).read("$.ver");
		String ui_name = JsonPath.parse(payload).read("$.ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}

	// POST - Create
	@RequestMapping(value = "/rest", params = "method=post", method = RequestMethod.POST)
	public Map<String, Object> post_post(@RequestBody String payload, HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new HashMap<String, Object>();

		String api_if = JsonPath.parse(payload).read("$.if");
		String ver = JsonPath.parse(payload).read("$.ver");
		String ui_name = JsonPath.parse(payload).read("$.ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}

	// POST - Put
	@RequestMapping(value = "/rest", params = "method=put", method = RequestMethod.POST)
	public Map<String, Object> post_put(@RequestBody String payload, HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new HashMap<String, Object>();

		String api_if = JsonPath.parse(payload).read("$.if");
		String ver = JsonPath.parse(payload).read("$.ver");
		String ui_name = JsonPath.parse(payload).read("$.ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}

	// POST - Delete
	@RequestMapping(value = "/rest", params = "method=delete", method = RequestMethod.POST)
	public Map<String, Object> post_delete(@RequestBody String payload, HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new HashMap<String, Object>();

		String api_if = JsonPath.parse(payload).read("$.if");
		String ver = JsonPath.parse(payload).read("$.ver");
		String ui_name = JsonPath.parse(payload).read("$.ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}

}
