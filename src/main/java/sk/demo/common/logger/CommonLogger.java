package sk.demo.common.logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

@Component
public class CommonLogger {

	private static final Logger logger = LoggerFactory.getLogger(CommonLogger.class);

	// 어플리케이션 이름 (e.g. xpg-mngr)
	@Value("${vcap.application.name}")
	String appName;

	// 어플리케이션 인스턴스 번호 (e.g. 0	)
	@Value("${vcap.application.instance.index}")
	String appIndex;

	@Autowired
	Gson gson;

	// JsonPath Read Exception null 처리 설정
	Configuration configuration = Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build();

	// API 호출시 Servlet 에러 발생시 로그 처리
	public void commonResLog(HttpServletRequest request, String api_if, String ver, String ui_name, String value,
			String message) {
		logger.info(convertResJson("RES", request, api_if, ver, ui_name, value, message));

	}

	// API 호출시 정상처리, Exception 발생시 로그처리
	public void commonResLog(HttpServletRequest request, String resBody) {
		logger.info(convertResJson("RES", request, resBody));

	}

	// Message Queue 발신/수진 로그 처리 - API 호출
	public void commonMsqLog(HttpServletRequest request, String send_recive_type, String value,
			Map<String, Object> message) {
		logger.info(convertMsqJson("MSQ", request, send_recive_type, value, message));

	}

	// Message Queue 발신/수진 로그 처리 - BATCH 호출
	public void commonMsqLog(String if_id, String ver, String ui_name, String send_recive_type, String value,
			Map<String, Object> message) {
		logger.info(convertMsqJson("MSQ", if_id, ver, ui_name, send_recive_type, value, message));
	}

	// Redis 호출 로그 처리 - API 호출
	public void commonRdsLog(HttpServletRequest request, String opcode, String value, String message) {
		logger.info(convertRdsJson("MSQ", request, opcode, value, message));

	}

	// Redis 호출 로그 처리 - BATCH 호출
	public void commonRdsLog(String if_id, String ver, String ui_name, String opcode, String value, String message) {
		logger.info(convertRdsJson("MSQ", if_id, ver, ui_name, opcode, value, message));

	}

	// API 호출시 정상처리, Exception 발생시 로그처리 Json 변환
	public String convertResJson(String logTyp, HttpServletRequest request, String resBody) {

		Map<String, Object> log = new LinkedHashMap<String, Object>();

		log.put("log_type", logTyp + "");
		log.put("app_name", this.appName + "");
		log.put("app_idex", this.appIndex + "");
		log.put("http_method", request.getMethod() + "");
		log.put("action_method", request.getParameter("method") + "");
		log.put("latency", request.getAttribute("latency") + "");
		log.put("if", JsonPath.using(configuration).parse(resBody).read("$.if") + "");
		log.put("ver", JsonPath.using(configuration).parse(resBody).read("$.ver") + "");
		log.put("ui_name", JsonPath.using(configuration).parse(resBody).read("$.ui_name") + "");
		log.put("value", JsonPath.using(configuration).parse(resBody).read("$.result") + "");
		log.put("message", JsonPath.using(configuration).parse(resBody).read("$.reason") + "");

		return gson.toJson(log);
	}

	// API 호출시 Servlet 에러 발생시 로그 처리 Json 변환
	public String convertResJson(String logTyp, HttpServletRequest request, String api_if, String ver, String ui_name,
			String result, String message) {
		Map<String, Object> log = new LinkedHashMap<String, Object>();

		log.put("log_type", logTyp + "");
		log.put("app_name", this.appName + "");
		log.put("app_idex", this.appIndex + "");
		log.put("http_method", request.getMethod() + "");
		log.put("action_method", request.getParameter("method") + "");
		log.put("latency", 0 + "");
		log.put("if", api_if + "");
		log.put("ver", ver + "");
		log.put("ui_name", ui_name + "");
		log.put("value", result + "");
		log.put("message", message + "");

		return gson.toJson(log);
	}

	// Message Queue 발신/수진 로그처리 Json 변환 - API 호출
	public String convertMsqJson(String logTyp, HttpServletRequest request, String send_recive_type, String value,
			Map<String, Object> message) {

		Map<String, Object> log = new LinkedHashMap<String, Object>();

		HashMap<String, String> loginfo = getErrorLogInfo(request);

		log.put("log_type", logTyp + "");
		log.put("app_name", this.appName + "");
		log.put("app_idex", this.appIndex + "");
		log.put("if", loginfo.get("if"));
		log.put("ver", loginfo.get("ver"));
		log.put("ui_name", loginfo.get("ui_name"));
		log.put("send_recive_type", send_recive_type);
		log.put("sender", message.get("SENDER"));
		log.put("reciver", message.get("RECEIVER"));
		log.put("opcode", message.get("OPCODE"));
		log.put("value", value);
		log.put("message", message.get("MESSAGE"));

		return gson.toJson(log);
	}

	// Message Queue 발신/수진 로그처리 Json 변환 - Batch
	public String convertMsqJson(String logTyp, String if_id, String ver, String ui_name, String send_recive_type,
			String value, Map<String, Object> message) {

		Map<String, Object> log = new LinkedHashMap<String, Object>();

		log.put("log_type", logTyp + "");
		log.put("app_name", this.appName + "");
		log.put("app_idex", this.appIndex + "");
		log.put("if", if_id);
		log.put("ver", ver);
		log.put("ui_name", ui_name);
		log.put("send_recive_type", send_recive_type);
		log.put("sender", message.get("SENDER"));
		log.put("reciver", message.get("RECEIVER"));
		log.put("opcode", message.get("OPCODE"));
		log.put("value", value);
		log.put("message", message.get("MESSAGE"));

		return gson.toJson(log);
	}

	// Redis 호출 처리 Json 변환 - API 호출
	public String convertRdsJson(String logTyp, HttpServletRequest request, String opcode, String value,
			String message) {

		Map<String, Object> log = new LinkedHashMap<String, Object>();

		HashMap<String, String> loginfo = getErrorLogInfo(request);

		log.put("log_type", logTyp + "");
		log.put("app_name", this.appName + "");
		log.put("app_idex", this.appIndex + "");
		log.put("if", loginfo.get("if"));
		log.put("ver", loginfo.get("ver"));
		log.put("ui_name", loginfo.get("ui_name"));
		log.put("opcode", opcode);
		log.put("value", value);
		log.put("message", message);

		return gson.toJson(log);
	}

	// Redis 호출 처리 Json 변환 - Batch
	public String convertRdsJson(String logTyp, String if_id, String ver, String ui_name, String opcode,
			String value, String message) {

		Map<String, Object> log = new LinkedHashMap<String, Object>();

		log.put("log_type", logTyp + "");
		log.put("app_name", this.appName + "");
		log.put("app_idex", this.appIndex + "");
		log.put("if", if_id);
		log.put("ver", ver);
		log.put("ui_name", ui_name);
		log.put("opcode", opcode);
		log.put("value", value);
		log.put("message", message);

		return gson.toJson(log);
	}

	// Servlet 에러 발생시 Log정보 설정
	public HashMap<String, String> getServletErrorLogInfo(HttpServletRequest request) {

		HashMap<String, String> logInfo = new HashMap<String, String>();

		logInfo.put("if", request.getParameter("if") + "");
		logInfo.put("ver", request.getParameter("ver") + "");
		logInfo.put("ui_name", request.getParameter("ui_name") + "");

		if (request.getMethod().equals(HttpMethod.POST.toString())) {

			String payload = "";
			try {
				payload = IOUtils.toString(request.getReader());

				logInfo.put("if", JsonPath.using(configuration).parse(payload).read("$.if") + "");
				logInfo.put("ver", JsonPath.using(configuration).parse(payload).read("$.ver") + "");
				logInfo.put("ui_name", JsonPath.using(configuration).parse(payload).read("$.ui_name") + "");

				System.out.println(payload);
			} catch (Exception e) {

			}

		}

		return logInfo;
	}

	// Exception 에러 발생시 Log정보 설정
	public HashMap<String, String> getErrorLogInfo(HttpServletRequest request) {

		HashMap<String, String> logInfo = new HashMap<String, String>();

		logInfo.put("if", request.getParameter("if") + "");
		logInfo.put("ver", request.getParameter("ver") + "");
		logInfo.put("ui_name", request.getParameter("ui_name") + "");

		if (request.getMethod().equals(HttpMethod.POST.toString())) {

			try {
				String payload = (String) request.getAttribute("payload");

				logInfo.put("if", JsonPath.using(configuration).parse(payload).read("$.if") + "");
				logInfo.put("ver", JsonPath.using(configuration).parse(payload).read("$.ver") + "");
				logInfo.put("ui_name", JsonPath.using(configuration).parse(payload).read("$.ui_name") + "");
			} catch (Exception e) {

			}
		}

		return logInfo;
	}
}