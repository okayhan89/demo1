package sk.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import sk.demo.common.logger.CommonLogger;
import sk.demo.config.RabbitMQConfig;

/**
 * RabbitMQ 샘픔
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value= "/v1")
//@EnableRabbit
public class RabbitMQServiceController{

	private static final Logger logger = LoggerFactory.getLogger(RestServiceController.class.getName());
	
	@Autowired
	CommonLogger commonLogger;
	
	//@Autowired
	//private RabbitAdmin rabbitAdmin;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@RequestMapping(value= "/rabbitmq", method=RequestMethod.GET)
	public Map<String, Object> main(HttpServletRequest request, HttpServletResponse response/*, @PathVariable String ver*/) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> node = new HashMap<String, Object>();
		
		Map<String, Object> message = new HashMap<String, Object>();

		message.put("TID", "bff.image." + UUID.randomUUID());
		message.put("TIMESTAMP", new SimpleDateFormat("yyyyMMdd hh:mm:ss").format(new Date(System.currentTimeMillis())));
		message.put("SENDER", "bff.image");
		message.put("RECEIVER", "bff.webclient");
		message.put("OP_CODE", "image_update");
		message.put("MESSAGE", "Image update Call message");
		message.put("XXX", "Etc Message");
		
		try {
			rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,"demo.image", message);
			commonLogger.commonMsqLog(request, "SEND", "SUCCESS", message);
		} catch (Exception e) {
			commonLogger.commonMsqLog(request,"SEND", "FAIL", message);
		}
		
		node.put("Node", "Value");
		result.put("Result", node);
		
		return result;
	}
	
	@RabbitListener(queues = "#{@queues[1]}")
    public void processMessage1(Map<String, Object> message) {
		//logger.debug(message.toString().getBytes().length+"");
		logger.debug("queues[1].demo.image TID       : "+ message.get("TID"));
		
		// Message 처리 로직
		commonLogger.commonMsqLog("BAT-0001-DEMO", "1.0", "NEXTUI", "RECIVE", "SUCCESS", message);
    }
	
	@RabbitListener(queues = "#{@queues[2]}")
    public void processMessage2(Map<String, Object> message) {
		//logger.debug(message.toString().getBytes().length+"");
		logger.debug("queues[2].demo.result TID       : "+ message.get("TID"));
    }
	
	@RabbitListener(queues = "#{@queues[3]}")
    public void processMessage3(Map<String, Object> message) {
		//logger.debug(message.toString().getBytes().length+"");
		logger.debug("queues[3].demo.image.index TID       : "+ message.get("TID"));
    }
	
	/*@RabbitListener(queues = "#{@queues[4]}")
    public void processMessage4(Message message) {
		//logger.debug(message.toString().getBytes().length+"");
		try {
			logger.debug("queues[4].demo.image.index TID       : "+ new String(message.getBody(), "UTF-8"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
    }*/
	
	@RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUE)
    public void processMessage4(Map<String, Object> message) {
		logger.debug("Dead Letter Quee******************************");
		logger.debug("TID       : "+ message.get("TID"));
    }
		
}
