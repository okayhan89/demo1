package sk.demo.config;

import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.api.types.Facing;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.identity.v3.Endpoint;
import org.openstack4j.model.identity.v3.Service;
import org.openstack4j.openstack.OSFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * Object Storate Config 샘픔
 * @author Administrator
 *
 */
@Configuration
public class SwiftConfig {

	@Value("${vcap.service.swift.username}")
	public String userName;
	
	@Value("${vcap.service.swift.password}")
	public String password;
	
	@Value("${vcap.service.swift.authurl}")
	public String authUrl;
	
	@Value("${vcap.service.swift.project}")
	public String project;
	
	@Value("${vcap.service.swift.domain}")
	public String domain;
	
	@Value("${vcap.service.swift.region}")
	public String region;
	
	public OSClientV3 getClient(){
		
		OSClientV3 os = OSFactory.builderV3().endpoint(this.authUrl)
				.credentials(this.userName, this.password, Identifier.byName(this.domain))
				.scopeToProject(Identifier.byName(this.project), Identifier.byName(this.domain))
				.authenticate();
		
		return os;
	}
	
	public String getPublicUrl(){
		OSClientV3 os = getClient();
		for (Service s : os.getToken().getCatalog()) {
            if (s.getName().equals("swift")) {
                for (Endpoint e : s.getEndpoints()) {
                    if (e.getRegion().equals(this.region) && e.getIface().equals(Facing.PUBLIC)) {
                        return e.getUrl().toString();
                    }
                }
            }
        }
		return "";
	}
}
