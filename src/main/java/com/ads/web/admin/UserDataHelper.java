package com.ads.web.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.ads.web.admin.model.CompanyModel;

@SpringBootApplication
@ConfigurationProperties("data")
public class UserDataHelper {
	private  Map<String, CompanyModel> user;

	public Map<String, CompanyModel> getUser() {
		return user;
	}

	public void setUser(Map<String, CompanyModel> user) {
		this.user = user;
	}
	

}
