package sk.demo.common.exception.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaswift.joss.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import sk.demo.common.exception.CustomException1;
import sk.demo.common.exception.ErrorMessage;
import sk.demo.common.logger.CommonLogger;
import sk.demo.common.exception.EnumExceptionMessage;

/**
 * 전연 예외 처리(Controller 예외처리)
 * 
 * @author Administrator
 *
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler implements ErrorController {

	private static final Logger logger = LoggerFactory.getLogger(CommonLogger.class);
	
	private static final String ERROR_PATH = "/error";

	@Value("${service.name:none}")
	String svc_name;

	@Autowired
	CommonLogger commonLogger;
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = CustomException1.class)
	public ErrorMessage handleBaseException(CustomException1 e, HttpServletRequest request) {
		
		if(logger.isInfoEnabled()) e.printStackTrace() ;
		
		HashMap<String, String> loginfo = commonLogger.getErrorLogInfo(request);
		
		String api_if = loginfo.get("if");
		String ver = loginfo.get("ver");
		String ui_name = loginfo.get("ui_name");
		
		ErrorMessage em = new ErrorMessage();
		em.setApi_if(api_if);
		em.setVer(ver);
		em.setUi_name(ui_name);
		em.setSvc_name(this.svc_name);
		em.setResult(EnumExceptionMessage.CUST_ERROR1.getCode());
		em.setReason(EnumExceptionMessage.CUST_ERROR1.getReasonPhrase());
		
		return em;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = NumberFormatException.class)
	public ErrorMessage handleNumberFormatException(NumberFormatException e, HttpServletRequest request) {
		
		if(logger.isInfoEnabled()) e.printStackTrace() ;
		
		HashMap<String, String> loginfo = commonLogger.getErrorLogInfo(request);
		
		String api_if = loginfo.get("if");
		String ver = loginfo.get("ver");
		String ui_name = loginfo.get("ui_name");
		
		ErrorMessage em = new ErrorMessage();
		em.setApi_if(api_if);
		em.setVer(ver);
		em.setUi_name(ui_name);
		em.setSvc_name(this.svc_name);
		em.setResult(EnumExceptionMessage.BAD_REQUEST.getCode());
		em.setReason(EnumExceptionMessage.BAD_REQUEST.getReasonPhrase());
		
		return em;
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = UnauthorizedException.class)
	public ErrorMessage handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request) {
		
		if(logger.isInfoEnabled()) e.printStackTrace() ;
		

		HashMap<String, String> loginfo = commonLogger.getErrorLogInfo(request);
		
		String api_if = loginfo.get("if");
		String ver = loginfo.get("ver");
		String ui_name = loginfo.get("ui_name");
		
		ErrorMessage em = new ErrorMessage();
		em.setApi_if(api_if);
		em.setVer(ver);
		em.setUi_name(ui_name);
		em.setSvc_name(this.svc_name);
		em.setResult(EnumExceptionMessage.UNAUTHORIZED_ERROR.getCode());
		em.setReason(EnumExceptionMessage.UNAUTHORIZED_ERROR.getReasonPhrase());
		
		return em;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ErrorMessage handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
		
		if(logger.isInfoEnabled()) e.printStackTrace() ;
		

		HashMap<String, String> loginfo = commonLogger.getErrorLogInfo(request);
		
		String api_if = loginfo.get("if");
		String ver = loginfo.get("ver");
		String ui_name = loginfo.get("ui_name");
		
		ErrorMessage em = new ErrorMessage();
		em.setApi_if(api_if);
		em.setVer(ver);
		em.setUi_name(ui_name);
		em.setSvc_name(this.svc_name);
		em.setResult(EnumExceptionMessage.BAD_REQUEST.getCode());
		em.setReason(EnumExceptionMessage.BAD_REQUEST.getReasonPhrase());
		
		return em;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = UnsatisfiedServletRequestParameterException.class)
	public ErrorMessage handleUnsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException e, HttpServletRequest request) {
		
		if(logger.isInfoEnabled()) e.printStackTrace() ;
		

		HashMap<String, String> loginfo = commonLogger.getErrorLogInfo(request);
		
		String api_if = loginfo.get("if");
		String ver = loginfo.get("ver");
		String ui_name = loginfo.get("ui_name");
		
		ErrorMessage em = new ErrorMessage();
		em.setApi_if(api_if);
		em.setVer(ver);
		em.setUi_name(ui_name);
		em.setSvc_name(this.svc_name);
		em.setResult(EnumExceptionMessage.BAD_REQUEST.getCode());
		em.setReason(EnumExceptionMessage.BAD_REQUEST.getReasonPhrase());
		
		return em;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public ErrorMessage handleException(Exception e, HttpServletRequest request) {
		
		if(logger.isInfoEnabled()) e.printStackTrace() ;

		HashMap<String, String> loginfo = commonLogger.getErrorLogInfo(request);
		
		String api_if = loginfo.get("if");
		String ver = loginfo.get("ver");
		String ui_name = loginfo.get("ui_name");
		
		ErrorMessage em = new ErrorMessage();
		em.setApi_if(api_if);
		em.setVer(ver);
		em.setUi_name(ui_name);
		em.setSvc_name(this.svc_name);
		em.setResult(EnumExceptionMessage.INTER_SERVER_ERROR.getCode());
		em.setReason(EnumExceptionMessage.INTER_SERVER_ERROR.getReasonPhrase());
		
		return em;
	}

	@Autowired
	private ErrorAttributes errorAttributes;
	
	/*
	 * Servlet 예외처리
	 */
	@RequestMapping(value = ERROR_PATH)
	public ErrorMessage handleError(HttpServletRequest request, HttpServletResponse response){

		Map<String, Object> errorAttributes = getErrorAttributes(request,false);
		if(logger.isInfoEnabled()) logger.error(errorAttributes.toString());

		int sts_cd;
		HashMap<String, String> loginfo = commonLogger.getServletErrorLogInfo(request);
		
		String api_if = loginfo.get("if");
		String ver = loginfo.get("ver");
		String ui_name = loginfo.get("ui_name");
				
		ErrorMessage em = new ErrorMessage();
		
		sts_cd = response.getStatus() > 499 ? 500 : 400;
		sts_cd = response.getStatus() == 404 ? 404 : sts_cd;

		em.setApi_if(api_if);
		em.setVer(ver);
		em.setUi_name(ui_name);
		em.setSvc_name(this.svc_name);
		em.setResult(EnumExceptionMessage.codeOf(sts_cd));
		em.setReason(EnumExceptionMessage.reasonPhraseOf(sts_cd));

		response.setStatus(sts_cd);
		
		commonLogger.commonResLog(request, api_if, ver, ui_name, EnumExceptionMessage.codeOf(sts_cd), EnumExceptionMessage.reasonPhraseOf(sts_cd));
		return em;
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}
	
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
