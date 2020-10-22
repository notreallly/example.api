package org.sysage.example.api.controller.redcap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/keycloak.properties")
public class KeyCloackConfig {

	@Value("${realm}")
	private String realm;

	@Value("${auth.server.url}")
	private String authServerUrl;

	@Value("${resource}")
	private String resource;

	public KeyCloackConfig() {
	}

	public KeyCloackConfig(String realm, String authServerUrl, String resource) {
		this.realm = realm;
		this.authServerUrl = authServerUrl;
		this.resource = resource;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getAuthServerUrl() {
		return authServerUrl;
	}

	public void setAuthServerUrl(String authServerUrl) {
		this.authServerUrl = authServerUrl;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@Override
	public String toString() {
		return "KeyCloackConfig [realm=" + realm + ", authServerUrl=" + authServerUrl + ", resource=" + resource + "]";
	}
}
