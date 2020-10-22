package org.sysage.example.api.controller.redcap;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private KeyCloackConfig config;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String addUsers(@RequestParam(required = true) String token, @RequestParam(required = true) String content,
			@RequestParam(required = true) String format, @RequestParam(required = true) String data,
			@RequestParam(required = false) String returnFormat, HttpServletResponse response) {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		User[] reqUser = null;
		try {
			reqUser = objectMapper.readValue(data, User[].class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Keycloak kc = Keycloak.getInstance(config.getAuthServerUrl(), config.getRealm(), config.getResource(), token);
		UsersResource resource = kc.realm(config.getRealm()).users();

		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue("123");

		UserRepresentation user = new UserRepresentation();
		user.setUsername(reqUser[0].getUsername());
		user.setEnabled(true);
		user.setCredentials(Arrays.asList(credential));

		Response serviceResp = resource.create(user);
		response.setStatus(serviceResp.getStatus());
		System.out.println(serviceResp.readEntity(String.class));
		CreatedResponseUtil.getCreatedId(serviceResp);

		return "";
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public String modifyUsers(@RequestParam(required = true) String token,
			@RequestParam(required = true) String content, @RequestParam(required = true) String format,
			@RequestParam(required = true) String data, @RequestParam(required = false) String returnFormat,
			HttpServletResponse response) {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		User[] reqUser = null;
		try {
			reqUser = objectMapper.readValue(data, User[].class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Keycloak kc = Keycloak.getInstance(config.getAuthServerUrl(), config.getRealm(), config.getResource(), token);
		UserResource resource = kc.realm(config.getRealm()).users().get(reqUser[0].getId());

		if (resource != null) {
			CredentialRepresentation credential = new CredentialRepresentation();
			credential.setType(CredentialRepresentation.PASSWORD);
			credential.setValue(reqUser[0].getPassword());
			credential.setTemporary(false);
			resource.resetPassword(credential);
			response.setStatus(204);
		} else
			response.setStatus(404);

		return "";
	}

	@ExceptionHandler
	@ResponseBody
	public ServiceError handleWebApplicationException(WebApplicationException exception, HttpServletResponse response) {
		response.setStatus(exception.getResponse().getStatus());
		return new ServiceError(exception.getMessage());
	}
}
