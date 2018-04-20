package sk.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import sk.demo.common.interceptor.HttpInterceptor;

@Configuration
public class CommonConfig extends WebMvcConfigurerAdapter{

	// Ribbon 
	@LoadBalanced
    @Bean
    RestTemplate loadBalanced() {
        return new RestTemplate(getClientHttpRequestFactory());
    }

    @Primary
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(getClientHttpRequestFactory());
    }
    
    @Bean
    ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 3000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
          = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

    @Autowired
    HttpInterceptor httpInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
    	registry.addInterceptor(httpInterceptor)
    	.addPathPatterns("/**");
    	//.excludePathPatterns("/user/**");
    	
    }
    
}
