package com.tm.crm.config;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.apache.oltu.oauth2.common.OAuth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tm.crm.Application;
import com.tm.crm.entity.Customer;
import com.tm.crm.repository.CustomerRepository;
import com.tm.crm.util.ResponseUtil;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes=Application.class)
public class AuthenticationTests {
	
	@Autowired
	WebApplicationContext context;
	
	@Autowired
	private FilterChainProxy springSecurityFilterChain;

    @Autowired
	CustomerRepository customerRepository;
    
    private MockMvc mvc;
    
    private static Customer customer;
    
    @Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilter(springSecurityFilterChain).build();

		if(customer == null){
			customer = new Customer();
			customer.setId(1L);
			customer.setEmail("user@tm.com");
			customer.setPassword("123456");
			customerRepository.save(customer);
		}
	}
    
    @Test
	public void usersEndpointAuthorized() throws Exception {
		mvc.perform(get("/customer/echo")
				.header("Authorization", "Bearer " + getAccessToken("user@tm.com", "123456")))
				.andExpect(status().isOk());
	}

    @Test
	public void usersEndpointAccessDenied() throws Exception {
    	mvc.perform(get("/customer/echo"))
				.andExpect(status().isUnauthorized());
	}
	
	private String getAccessToken(String username, String password) throws Exception {

		MockHttpServletResponse response = mvc
				.perform(
						post("/oauth/token")
								.contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.param(OAuth.OAUTH_CLIENT_ID, "WEB")
								.param(OAuth.OAUTH_USERNAME, username)
								.param(OAuth.OAUTH_PASSWORD, password)
								.param(OAuth.OAUTH_GRANT_TYPE, "password"))
				
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.access_token", is(notNullValue())))
				.andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
				.andExpect(jsonPath("$.refresh_token", is(notNullValue())))
//				.andExpect(jsonPath("$.expires_in", is(greaterThan(40000))))
				.andExpect(jsonPath("$.scope", is(equalTo("read write"))))
				
				.andReturn().getResponse();

		String content = response.getContentAsString();
		Map<String, Object> responseMap = ResponseUtil.getResponseAsMap(content);
		return responseMap.get("access_token").toString();
	}

}