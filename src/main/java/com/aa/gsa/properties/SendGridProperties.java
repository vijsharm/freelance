package com.aa.gsa.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConfigurationProperties(prefix = "sendgrid")
public class SendGridProperties {
	
	@Value("${sendgrid.api-key}")
	private String apiKey;
	
	@Value("${sendgrid.from-email}")
	private String fromEmail;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	

}
