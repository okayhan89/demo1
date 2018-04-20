package sk.demo.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.jsonpath.JsonPath;

import sk.demo.common.exception.CustomException1;
import sk.demo.common.exception.CustomException2;
import sk.demo.common.exception.EnumExceptionMessage;
import sk.demo.common.exception.ErrorMessage;
import sk.demo.common.logger.CommonLogger;

/**
 * 예외처리 샘픔
 * 
 * @author ekansh
 * @since 19/2/16
 */
@RestController
@RequestMapping(value = "/v1")
public class ExceptionController {

	private static final Logger logger = LoggerFactory.getLogger(EurekaServiceController.class.getName());
	
	@Value("${service.name:none}")
	String SvcNm;
	
	@Autowired
	CommonLogger commonLogger;
	
	@RequestMapping("/ex1")
	public String ex1(HttpServletRequest request, HttpServletResponse response/*, @PathVariable String ver*/) {
		// 전역 처리자 메소드 handleBaseException에 잡힐 것이다.
		
		throw new CustomException1("CustomException1");
	}

	@RequestMapping("/ex2")
	public String ex2(HttpServletRequest request, HttpServletResponse response/*, @PathVariable String ver*/) {
		// 전역 처리자 메소드 handleBaseException에 잡힐 것이다.
		
		throw new CustomException2("CustomException2");
	}

	@RequestMapping("/ex3")
	public String ex3(HttpServletRequest request, HttpServletResponse response/*, @PathVariable String ver*/) {
		// 전역 처리자 메소드 handleBaseException에 잡힐 것이다.
		
		throw new NumberFormatException("NumberFormatException");
	}
	
	@RequestMapping("/ex4")
	public String ex4(HttpServletRequest request, HttpServletResponse response/*, @PathVariable String ver*/) {
		// 전역 처리자 메소드 handleBaseException에 잡힐 것이다.
		
		throw new NullPointerException("NullPointerException");
	}
	
	@RequestMapping("/ex5")
	public String ex5(HttpServletRequest request, HttpServletResponse response/*, @PathVariable String ver*/) {
		// 전역 처리자 메소드 handleBaseException에 잡힐 것이다.
		
		throw new IllegalArgumentException("IllegalArgumentException");
	}

	/**
	 * 이 컨트롤러 내에서 발생하는 모든IllegalArgumentException 예외를 처리한다 *
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ErrorMessage nfeHandler(IllegalArgumentException e, HttpServletRequest request) {
		
		if(logger.isInfoEnabled()) e.printStackTrace();
		
		HashMap<String, String> loginfo = commonLogger.getErrorLogInfo(request);
		
		String api_if = loginfo.get("if");
		String ver = loginfo.get("ver");
		String ui_name = loginfo.get("ui_name");
		
		ErrorMessage em = new ErrorMessage();
		em.setApi_if(api_if);
    	em.setVer(ver);
    	em.setUi_name(ui_name);
    	em.setSvc_name(this.SvcNm);
    	em.setResult(EnumExceptionMessage.BAD_REQUEST.getCode());
    	em.setReason(EnumExceptionMessage.BAD_REQUEST.getReasonPhrase());
    	
		return em;
	}

}
