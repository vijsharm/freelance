package com.aa.gsa.config;

import java.net.MalformedURLException;
import java.net.URL;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.properties.CloudantProperties;
import com.aa.gsa.properties.DataSourceProperties;
import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;

@Configuration
@Profile("default")
@EnableConfigurationProperties(
		{DataSourceProperties.class, 
		 CloudantProperties.class}
		)
public class LocalConfig {

	@Autowired
	private CloudantProperties cloudantProperties;

	@Autowired
	private DataSourceProperties dataSourceProperties;

	private static final String LOCAL_CONFIG_FILE = "localconfig.yml";
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new ClassPathResource(LOCAL_CONFIG_FILE));
		propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
		return propertySourcesPlaceholderConfigurer;
	}

	@Bean
	public CloudantClient cloudantClient() {
		try {	
			return ClientBuilder
					.url(new URL(cloudantProperties.getUrl()))
					.build();
		} catch (MalformedURLException e) {
			throw new PointsProcessorException("Malformed Cloudant URL "+cloudantProperties.getUrl(), e);
		}
	}

	@Bean
	public DataSource getDataSource() {
		return DataSourceBuilder
				.create()
				.url(dataSourceProperties.getJdbcurl())
				.username(dataSourceProperties.getUsername())
				.password(dataSourceProperties.getPassword())
				.driverClassName(dataSourceProperties.getDriverclassname())
				.build();
	}
}
