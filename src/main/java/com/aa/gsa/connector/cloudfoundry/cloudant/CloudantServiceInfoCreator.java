package com.aa.gsa.connector.cloudfoundry.cloudant;

import java.util.Map;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

public class CloudantServiceInfoCreator extends CloudFoundryServiceInfoCreator<CloudantServiceInfo> {
	
	/**
	 * Constructor.
	 */
	public CloudantServiceInfoCreator() {
		super(new Tags());
	}

	@Override
	public boolean accept(final Map<String, Object> serviceData) { 
		String name = (String) serviceData.get("name");
		String label = (String) serviceData.get("label");
		return name.startsWith("Cloudant") || label.startsWith("cloudant");
	}

	@Override
	public CloudantServiceInfo createServiceInfo(final Map<String, Object> serviceData) {
		String id = null;
		String username = null;
		String password = null;
		String host = null;
		int port = 0;
		String url = null;
		Object credObject = serviceData.get("credentials");
		Object idObj = serviceData.get("name");
		if (idObj instanceof String) {
			id = (String) idObj;
		}
		if (credObject instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			final Map<String, Object> credentials = (Map<String, Object>) credObject;
			final Object usernameObj = credentials.get("username");
			final Object passwordObj = credentials.get("password");
			final Object hostObj = credentials.get("host");
			final Object portObj = credentials.get("port");
			final Object urlObj = credentials.get("url");
			if (usernameObj instanceof String) {
				username = (String) usernameObj;
			}
			if (passwordObj instanceof String) {
				password = (String) passwordObj;
			}
			if (hostObj instanceof String) {
				host = (String) hostObj;
			}
			if (portObj instanceof Integer) {
				port = (Integer) portObj;
			}
			if (urlObj instanceof String) {
				url = (String) urlObj;
			}
		}

		return new CloudantServiceInfo(id, username, password, host, port, url);
	}
}
