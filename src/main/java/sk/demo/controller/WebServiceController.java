package sk.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MVC 모델 샘플
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value= "/v1")
public class WebServiceController{

	@RequestMapping(value= "/web")
	public String main(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		model.put("message", "Welcome");
		
		return "main";
	}
}
