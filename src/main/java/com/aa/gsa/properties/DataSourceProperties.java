package com.aa.gsa.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="datasource")
public class DataSourceProperties {

	private String jdbcurl;
	
	private String username;
	
	private String password;
	
	private String driverclassname;

	public String getJdbcurl() {
		return jdbcurl;
	}

	public void setJdbcurl(String jdbcurl) {
		this.jdbcurl = jdbcurl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverclassname() {
		return driverclassname;
	}

	public void setDriverclassname(String driverclassname) {
		this.driverclassname = driverclassname;
	}
}
