package sk.demo.config;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.cloud.service.common.AmqpServiceInfo;
import org.springframework.cloud.service.document.MongoDbFactoryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
import com.rabbitmq.client.ConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Config 샘플
 */
@Profile("sample")//==>Cloud환경 인식하는 Annotation(manifest.yml의 Env정보와 매팽)
@Configuration//==>Spring Config 
@ServiceScan//==>Cloud Config Service Scan Annotation 
public class CloudDataConfig extends AbstractCloudConfig {

	//@Value("${db.mysql.servicename}")
	//private String mysqlServiceName;//==>application-cloud.properties의 mysql 	서비스 인스턴스명 읽어온다.
	//private String cubridJdbcUrl;

	@Value("${mongodb.service.name}")
	private String mongoServiceName;//==>application-cloud.properties의 mongodb	서비스 인스턴스명 읽어온다.
	@Value("${redis.service.name}")
	private String redisServiceName;//==>application-cloud.properties의 redis 	서비스 인스턴스명 읽어온다 
	@Value("${rabbitmq.service.name}")
	private String rabbitServiceName;//==>application-cloud.properties의 redis	서비스 인스턴스명 읽어온다

	/*@Bean(name="dsMysql")
	@Primary
	DataSource mysqlDataSource() {

		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
		ServiceInfo serviceInfo = cloud.getServiceInfo(mysqlServiceName);
		String serviceId = serviceInfo.getId();
		return cloud.getServiceConnector(serviceId, DataSource.class, null);

	}

	@Bean(name = "jdbcMysql") // ==> MySql 서비스 Connection설정
	@Autowired
	public JdbcTemplate mysqlJdbcTemplate(@Qualifier("dsMysql") DataSource dsSlave) {
		return new JdbcTemplate(dsSlave);
	}*/

	/*@Bean(name = "dsCubrid")
	public DataSource cubridDataSource() {
		try {

			String vcap_services = System.getenv("VCAP_SERVICES");
			JsonObject jsonObj = JSONObject.fromObject(vcap_services);
			JSONArray userPro = jsonObj.getJSONArray("CubridDB");
			jsonObj = JSONObject.fromObject(userPro.get(0));
			jsonObj = jsonObj.getJSONObject("credentials");
			cubridJdbcUrl = jsonObj.getString("jdbcurl");

			return new SimpleDriverDataSource(cubrid.jdbc.driver.CUBRIDDriver.class.newInstance(), cubridJdbcUrl);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}*/

	/*@Bean(name = "jdbcCubrid")  ==> Cubrid 서비스 Connection설정

	@Autowired
	public JdbcTemplate cubridJdbcTemplate(@Qualifier("dsCubrid") DataSource dsSlave) {
		return new JdbcTemplate(dsSlave);
	}
*/
	/**
	 * MongoDBFactory
	 */
	/*@Autowired
	MongoDbFactory mongoDbFactory;
	@Bean(name = "mongoTemplate")//   ==> MongoDB 서비스 Connection설정
	public MongoTemplate mongoTemplate() throws UnknownHostException {

		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
		MongoServiceInfo serviceInfo = (MongoServiceInfo) cloud.getServiceInfo(mongoServiceName);

		// MongoDB 인증 처리
		mongoDbFactory.getDb().authenticate(serviceInfo.getUserName(), serviceInfo.getPassword().toCharArray());
		mongoDbFactory = new mongodb
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);

		return mongoTemplate;
	}*/
	
	@Bean(name = "mongoFactory")
	public MongoDbFactory mongoFactory() {
	    MongoDbFactoryConfig mongoConfig = new MongoDbFactoryConfig("NONE", 50, 200);
	    return connectionFactory().mongoDbFactory(mongoServiceName, mongoConfig);
	}
	
	@Bean(name = "mongoTemplate")//   ==> MongoDB 서비스 Connection설정
	public MongoTemplate mongoTemplate(@Qualifier("mongoFactory")MongoDbFactory mongoDbFactory) throws UnknownHostException {

		//CloudFactory cloudFactory = new CloudFactory();
		//Cloud cloud = cloudFactory.getCloud();
		//MongoServiceInfo serviceInfo = (MongoServiceInfo) cloud.getServiceInfo(mongoServiceName);

		// MongoDB 인증 처리
		//mongoDbFactory.getDb().authenticate(serviceInfo.getUserName(), serviceInfo.getPassword().toCharArray());
		//mongoDbFactory = new mongodb
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);

		return mongoTemplate;
	}
	
	
	

	/*@Bean//  ==> Redis 서비스 Connection설정
	public JedisPool redisTemplate() {

		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
		RedisServiceInfo serviceInfo = (RedisServiceInfo) cloud.getServiceInfo(redisServiceName);
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(128);
		return new JedisPool(poolConfig, serviceInfo.getHost(), serviceInfo.getPort(), 5, serviceInfo.getPassword());
	}*/
	
	@Bean
	public JedisConnectionFactory redisFactory() {
		
		/*String vcap_services = System.getenv("VCAP_SERVICES");
		JsonObject jsonObj = gson.fromJson(vcap_services, JsonElement.class).getAsJsonObject();

		JsonArray userPro = jsonObj.getAsJsonArray(redisServiceName);
		jsonObj = userPro.get(0).getAsJsonObject().getAsJsonObject("credentials");
		
		String redisNodes = jsonObj.get("redisNodes").getAsString();
		String timeout = jsonObj.get("timeout").getAsString();
		String maxRedirects = jsonObj.get("maxRedirects").getAsString();
		
        
        source.put("spring.redis.cluster.nodes", redisNodes);
        source.put("spring.redis.cluster.timeout", timeout);
        source.put("spring.redis.cluster.max-redirects", maxRedirects);
        //source.put("spring.redis.password", redisPassword);
        */
		Map<String, Object> source = new HashMap<String, Object>();
        RedisClusterConfiguration redisClusterConfiguration = 
        		new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
        
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
		
	  return new JedisConnectionFactory(redisClusterConfiguration, poolConfig);
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

	@Bean
	@Primary
	public CachingConnectionFactory rabbitmqConnectionFactory() throws IOException {

		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();

		AmqpServiceInfo serviceInfo = (AmqpServiceInfo) cloud.getServiceInfo(rabbitServiceName);

		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(serviceInfo.getHost());
		connectionFactory.setPort(serviceInfo.getPort());
		connectionFactory.setUsername(serviceInfo.getUserName());
		connectionFactory.setPassword(serviceInfo.getPassword());
		connectionFactory.setVirtualHost(serviceInfo.getVirtualHost());

		try {
			// SslProtocol 사용 설정
			connectionFactory.useSslProtocol("TLS");

		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);

		return cachingConnectionFactory;
	}

	@Bean  //  ==> RabbitMQ 서비스 Connection설정
	public RabbitTemplate amqpTemplate(@Qualifier("rabbitmqConnectionFactory") CachingConnectionFactory connectionFactory) {

		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

		rabbitTemplate.setQueue("sampleQueue");
		rabbitTemplate.setRoutingKey("sampleQueue");
		rabbitTemplate.setConnectionFactory(connectionFactory);
		return rabbitTemplate;
	}

	@Bean
	public RabbitAdmin rabbitAdmin(@Qualifier("rabbitmqConnectionFactory") CachingConnectionFactory connectionFactory) {

		RabbitAdmin admin = new RabbitAdmin(connectionFactory);
		return admin;
	}

	//@Autowired
	//Gson gson;
	@Bean//  ==> GlusterFs 서비스 Connection설정
	public AccountConfig accountConfig() {

		/*String vcap_services = System.getenv("VCAP_SERVICES");
		JsonObject jsonObj = gson.fromJson(vcap_services, JsonElement.class).getAsJsonObject();

		JsonArray userPro = jsonObj.getAsJsonArray("glusterfs");
		jsonObj = userPro.get(0).getAsJsonObject().getAsJsonObject("credentials");
		// String provider = jsonObj.get("provider").getAsString();
		String tenantName = jsonObj.get("tenantname").getAsString();
		String username = jsonObj.get("username").getAsString();
		String password = jsonObj.get("password").getAsString();
		String authUrl = jsonObj.get("auth_url").getAsString();

		config.setUsername(username);
		config.setTenantName(tenantName);
		config.setPassword(password);
		config.setAuthUrl(authUrl + "/tokens");
		config.setAuthenticationMethod(AuthenticationMethod.KEYSTONE);*/
		AccountConfig config = new AccountConfig();
		return config;
	}

}