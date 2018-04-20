package sk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class DemoApplication extends SpringBootServletInitializer {
 
	
	/**
	 * War 패포시 설정
	 */
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(DemoApplication.class);
	}*/
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
