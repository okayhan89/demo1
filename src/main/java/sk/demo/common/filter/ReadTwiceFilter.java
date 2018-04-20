package sk.demo.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import sk.demo.common.logger.CommonLogger;

@Component
public class ReadTwiceFilter implements Filter {

	@Autowired
	CommonLogger commonLogger;
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
		
		// Request, Response의 Body 정보를 읽어오기
	    ReadTwiceHttpServletRequestWrapper readTwiceHttpServletRequestWrapper = new ReadTwiceHttpServletRequestWrapper((HttpServletRequest) request);
	    ReadTwiceHttpServletResponseWrapper readTwiceHttpServletResponseWrapper =  new ReadTwiceHttpServletResponseWrapper((HttpServletResponse)response); 

	    // RequestBody의 Payload 정보를 Request의 Attribute로 저장합니다.
	    String reqBody = readTwiceHttpServletRequestWrapper.getBody();
	    request.setAttribute("payload", reqBody);
	    
	    // Request Time을 설정합니다.
	    request.setAttribute("reqTime", System.currentTimeMillis());

	    // RequestBody에서 읽어온 Body 정보를 다시 입력합니다.
	    readTwiceHttpServletRequestWrapper.setBody(reqBody);
	    chain.doFilter(readTwiceHttpServletRequestWrapper, readTwiceHttpServletResponseWrapper);
	    
	    // Response시점에 공통 로그 클래스를 이용하여 결과 로그를 남깁니다.
	    if(response.getContentType() != null && response.getContentType().indexOf("application/json") >= 0){
	    	String resBody = readTwiceHttpServletResponseWrapper.getCaptureAsString();
	    	request.setAttribute("latency", (System.currentTimeMillis() - ((long)request.getAttribute("reqTime"))) / 1000.0);
	    	commonLogger.commonResLog((HttpServletRequest)request, resBody);
	    	response.getWriter().write(resBody);
	    };
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,filterConfig.getServletContext());
	}
}
