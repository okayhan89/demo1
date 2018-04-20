package sk.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.api.types.Facing;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.identity.v3.Endpoint;
import org.openstack4j.model.identity.v3.Service;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.model.storage.object.SwiftObject;
import org.openstack4j.model.storage.object.options.CreateUpdateContainerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sk.demo.common.exception.CustomException1;
import sk.demo.config.SwiftConfig;

@RestController
@RequestMapping(value = "/v1")
public class SwiftServiceController {

	private static final Logger logger = LoggerFactory.getLogger(SwiftServiceController.class.getName());

	@Autowired
	SwiftConfig swiftConfig;

	/**
	 * Swift test
	 */
	@RequestMapping(value = "/swift", params = "method=post", method = RequestMethod.POST)
	public Map<String, Object> saveFile(@RequestParam MultipartFile attchFile, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("start swift service");
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> node = new HashMap<String, Object>();

		OSClientV3 os = swiftConfig.getClient();
		
		String containerName = "myContainer";
		String objectName = System.currentTimeMillis() + "-" + attchFile.getOriginalFilename();
		// 컨테이너 생성
		os.objectStorage().containers().create(containerName,
				CreateUpdateContainerOptions.create().accessAnybodyRead());

		// 컨테이너 목록 조회
		List<? extends SwiftContainer> containers = (List<? extends SwiftContainer>) os.objectStorage().containers().list();
		for (SwiftContainer con : containers) {
			logger.debug("container name : " + con.getName());
		}

		// Object 업로드
		try {
			os.objectStorage().objects().put(containerName, objectName, Payloads.create(attchFile.getInputStream()));
			logger.debug("object name : " + os.objectStorage().objects().getMetadata(containerName, objectName).toString());
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Object 목록
		List<? extends SwiftObject> objects = (List<? extends SwiftObject>) os.objectStorage().objects().list(containerName);
		for (SwiftObject obj : objects) {
			logger.debug("object name : " + obj.getName());
		}

		node.put("Node", "object");
		node.put("PublicURL", swiftConfig.getPublicUrl() + "/" + containerName + "/" + objectName);
		result.put("Result", node);

		return result;
	}

	@RequestMapping(value = "/swift", params = "method=delete", method = RequestMethod.POST)
	public Map<String, Object> deleteFile(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> node = new HashMap<String, Object>();

		OSClientV3 os = swiftConfig.getClient();
		
		String containerName = "myContainer";
		String objectName = "";

		// 컨테이너 목록 조회
		List<? extends SwiftContainer> containers = (List<? extends SwiftContainer>) os.objectStorage().containers().list();
		for (SwiftContainer con : containers) {
			logger.debug("container name : " + con.getName());
		}

		// Object 목록
		List<? extends SwiftObject> objects = (List<? extends SwiftObject>) os.objectStorage().objects().list(containerName);
		for (SwiftObject obj : objects) {
			logger.debug("object name : " + obj.getName());
			objectName = obj.getName();
		}

		// Download & 복사본 업로드
		os.objectStorage().objects().put(containerName, "copy-" + objectName, 
				Payloads.create(os.objectStorage().objects().download(containerName, objectName).getInputStream()));
		
		// 복사 결과 확인
		objects = (List<? extends SwiftObject>) os.objectStorage().objects().list(containerName);
		for (SwiftObject obj : objects) {
			logger.debug("object name : " + obj.getName());
		}

		// 삭제
		os.objectStorage().objects().delete(containerName, "copy-" + objectName);
		
		// 삭제 결과 확인 & 전체 삭제
		objects = (List<? extends SwiftObject>) os.objectStorage().objects().list(containerName);
		for (SwiftObject obj : objects) {
			logger.debug("object name : " + obj.getName());
			os.objectStorage().objects().delete(containerName, obj.getName());
		}
		
		node.put("Node", "object");
		result.put("Result", node);

		return result;
	}
}
