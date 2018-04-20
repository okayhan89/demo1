package sk.demo.config.bluemix;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis Config 연동 샘픔
 * @author Administrator
 *
 */
@Profile({ "dev", "stg", "prd" }) // Cloud환경 인식하는 Annotation(manifest.yml의 Env정보와 매팽)
@Configuration // Spring Config
public class RedisCloudConfig/*extends AbstractCloudConfig*/{

	@Value("${redis.service.name:demo-redis-cluster}")
	private String redisServiceName;		// application-cloud.properties의 redis 서비스 인스턴스명 읽어온다
	
	@Value("${redis.service.timeout:400}")
	private String timeout;					// application-cloud.properties의 redis 서비스 인스턴스명 읽어온다
	
	@Value("${redis.service.maxredirects:1}")
	private String maxRedirects;			// application-cloud.properties의 redis 서비스 인스턴스명 읽어온다
	
	@Value("${vcap.service.redis.nodes}")
	private String nodes;
	
	@Value("${spring.redis.password}")
	private String password;


	/* 클러스터 구성시 */
	/*
	@Bean
	public RedisClusterConfiguration getClusterConfiguration() {

		Map<String, Object> source = new HashMap<String, Object>();

		source.put("spring.redis.cluster.nodes", this.nodes);
		source.put("spring.redis.cluster.timeout", timeout);
		source.put("spring.redis.cluster.max-redirects", maxRedirects);
		// source.put("spring.redis.password", this.password);

		return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
	}
	*/

	@Bean
	public JedisConnectionFactory redisFactory() {
			
		/* 클러스터 구성시 */
		/*
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(600);
		poolConfig.setMaxIdle(60);
		poolConfig.setMinIdle(30);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
		poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
		poolConfig.setNumTestsPerEvictionRun(3);
		poolConfig.setBlockWhenExhausted(true);

		return new JedisConnectionFactory(getClusterConfiguration(), poolConfig);
		*/
		
		/* 단일 구성시 */		
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(this.nodes);
		//factory.setHostName("169.56.79.184");
		factory.setPort(6379);
		factory.setPassword(password);
		factory.setUsePool(true);
		factory.afterPropertiesSet();
		return factory;
	}

	@Bean
	RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate<?, ?> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(redisFactory());
		return redisTemplate;
	}

	@Bean
	RedisCacheManager cacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
		return redisCacheManager;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}