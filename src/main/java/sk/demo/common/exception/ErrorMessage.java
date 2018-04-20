package sk.demo.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * 에레 메세지
 */
public class ErrorMessage {
	
	@JsonProperty(value="if")
	private String api_if;
	@JsonProperty(value="ver")
	private String ver;
	@JsonProperty(value="ui_name")
	private String ui_name;
	@JsonProperty(value="svc_name")
	private String svc_name;
	@JsonProperty(value="result")
	private String result;
	@JsonProperty(value="reason")
	private String reason;
	
	
	public String getApi_if() {
		return api_if;
	}
	public void setApi_if(String api_if) {
		this.api_if = api_if;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getUi_name() {
		return ui_name;
	}
	public void setUi_name(String ui_name) {
		this.ui_name = ui_name;
	}
	public String getSvc_name() {
		return svc_name;
	}
	public void setSvc_name(String svc_name) {
		this.svc_name = svc_name;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}	
	
	
	
}
