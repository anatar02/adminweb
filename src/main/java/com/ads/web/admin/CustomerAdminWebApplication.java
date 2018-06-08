package com.ads.web.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SpringBootApplication
@ConfigurationProperties("settings")
public class CustomerAdminWebApplication {
	private  Map<String, FormSettingModel> issueTypes;
	private  Map<String, StatusUIModel> status;
	

	public static void main(String[] args) {
		SpringApplication.run(CustomerAdminWebApplication.class, args);
	}


	public Map<String, FormSettingModel> getIssueTypes() {
		return issueTypes;
	}


	public void setIssueTypes(Map<String, FormSettingModel> issueTypes) {
		this.issueTypes = issueTypes;
	}


	public Map<String, StatusUIModel> getStatus() {
		return status;
	}


	public void setStatus(Map<String, StatusUIModel> status) {
		this.status = status;
	}
}
