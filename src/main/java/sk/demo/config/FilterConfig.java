package sk.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.demo.common.filter.ReadTwiceFilter;

@Configuration
public class FilterConfig
{
	@Bean
	public FilterRegistrationBean getFilterRegistrationBean()
	{
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(new ReadTwiceFilter());
		registrationBean.addUrlPatterns("/v1/*");
		return registrationBean;
	}
}
