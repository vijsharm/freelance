package com.aa.gsa.connector.cloudfoundry.cloudant;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.cloud.service.AbstractServiceConnectorCreator;
import org.springframework.cloud.service.ServiceConnectorConfig;

import com.aa.gsa.exception.PointsProcessorException;
import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.http.interceptors.Replay429Interceptor;

/**
 * Cloudant Service Connector 
 * @author 940914
 */
public class CloudantServiceConnectorCreator extends AbstractServiceConnectorCreator<CloudantClient, CloudantServiceInfo> {

	@Override
	public CloudantClient create(CloudantServiceInfo serviceInfo, ServiceConnectorConfig serviceConnectorConfig) {
		try {
			return ClientBuilder
					.url(new URL(serviceInfo.getUrl()))
					.interceptors(Replay429Interceptor.WITH_DEFAULTS)
					.build();
		}   catch (MalformedURLException e) {
			throw new PointsProcessorException("Malformed Cloudant URL "+serviceInfo.getUrl(), e);
		}
	}
}
