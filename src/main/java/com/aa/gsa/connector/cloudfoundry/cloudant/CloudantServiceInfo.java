package com.aa.gsa.connector.cloudfoundry.cloudant;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import org.springframework.cloud.service.BaseServiceInfo;

public class CloudantServiceInfo extends BaseServiceInfo {
	/**
	 * Scheme. This is not a real scheme but is needed for local config to work.
	 */
	public static final String SCHEME = "couchdb";

	private String username;
	private String password;
	private String host;
	private int port;
	private String url;

	/**
	 * Constructor.
	 *
	 * @param id The service id.
	 * @param username The username for the service.
	 * @param password The password for the service.
	 * @param host The host name for the service.
	 * @param port The port for the service.
	 * @param url The URL to the service. This should include the username and
	 * password.
	 */
	public CloudantServiceInfo(String id, String username, String password, String host, int port, String url) {
		super(id);
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
		this.url = url;
	}

	public CloudantServiceInfo(String id, String url) throws URISyntaxException {
		super(id);
		URI uri = new URI(url);
		this.url = url.replaceFirst(CloudantServiceInfo.SCHEME, "http");
		this.host = uri.getHost();
		this.port = uri.getPort();
		String serviceInfoString = uri.getUserInfo();
		if (serviceInfoString != null) {
			String[] userInfo = serviceInfoString.split(":");
			this.username = userInfo[0];
			this.password = userInfo[1];
		}
	}

	/**
	 * Gets the username.
	 *
	 * @return The username.
	 */
	@ServiceProperty
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the password.
	 *
	 * @return The password.
	 */
	@ServiceProperty
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the host.
	 *
	 * @return The host.
	 */
	@ServiceProperty
	public String getHost() {
		return host;
	}

	/**
	 * Gets the port.
	 *
	 * @return The port.
	 */
	@ServiceProperty
	public int getPort() {
		return port;
	}

	/**
	 * Gets the URL.
	 *
	 * @return The URL.
	 */
	@ServiceProperty
	public String getUrl() {
		return url;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash + Objects.hashCode(this.username);
		hash = 47 * hash + Objects.hashCode(this.password);
		hash = 47 * hash + Objects.hashCode(this.host);
		hash = 47 * hash + this.port;
		hash = 47 * hash + Objects.hashCode(this.url);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final CloudantServiceInfo other = (CloudantServiceInfo) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		if (!Objects.equals(this.username, other.username)) {
			return false;
		}
		if (!Objects.equals(this.password, other.password)) {
			return false;
		}
		if (!Objects.equals(this.host, other.host)) {
			return false;
		}
		if (!Objects.equals(this.url, other.url)) {
			return false;
		}
		if (this.port != other.port) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CloudantServiceInfo"
				+ '{'
				+ "id=" + id + ", "
				+ "url=" + url + ", "
				+ "host=" + host + ", "
				+ "port=" + port + ", "
				+ "username=" + username + ", "
				+ "password=" + password
				+ '}';
	}
}
