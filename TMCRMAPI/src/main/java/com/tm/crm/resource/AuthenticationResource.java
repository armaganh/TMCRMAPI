package com.tm.crm.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
class AuthenticationResource {
	
	private static String API_ACCESS_TOKEN_RESOURCE = "http://localhost:8090/api/oauth/token";
	private static String API_CLIENT_ID = "WEB";
	
	@RequestMapping(value = "/echo", method = RequestMethod.GET)
	public String echo() {
        return "echo";
    }
	
	@RequestMapping(value="/login", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestBody String credentials) throws JsonProcessingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode credentialsObj = mapper.readTree(credentials);
	    String username = credentialsObj.get("username").asText();
	    String password = credentialsObj.get("password").asText();
	    
	    /** TOKEN BASED (OAUTH2.0)+ SPRING SECURITY **/
	    return authenticateOauth(username, password);
	}
	
	private ResponseEntity<?> authenticateOauth(String username, String password) {

		System.out.println("TOKEN SERVICE STARTED.");

		/*
		 * Make a /token call using the applications OAuth keys (a client_id and secret) for the basic authentication values 
		 * -u {clientId:secret} and In the request body, set grant_type to client_credentials.
		 * 
		 */
		OAuthClientRequest request = null;
		try {
			request = OAuthClientRequest
					.tokenLocation(API_ACCESS_TOKEN_RESOURCE)
					.setClientId(API_CLIENT_ID)
					.setUsername(username)
					.setPassword(password)
					.setGrantType(GrantType.PASSWORD)
					.buildBodyMessage();
		} catch (OAuthSystemException e) {
			e.printStackTrace();
		}
		System.out.println(request.getLocationUri());
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

		/*
		 * API generates and returns a new access token.
		 */
		OAuthAccessTokenResponse oauthResponse;
		try {
			oauthResponse = oAuthClient.accessToken(request);
		} catch (OAuthSystemException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (OAuthProblemException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		System.out.println("Access Token: " + oauthResponse.getAccessToken());
		System.out.println("Expires In: " + oauthResponse.getExpiresIn());
		
		Map<String, Object> token = new HashMap<String,Object>();
		token.put("token", oauthResponse.getAccessToken());
		token.put("expiresIn", oauthResponse.getExpiresIn());
		
		return new ResponseEntity<Map<String, Object>>(token, HttpStatus.OK);
	}
	
}
