package com.tm.crm.rest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tm.crm.Application;
import com.tm.crm.entity.Customer;
import com.tm.crm.enums.CustomerGender;
import com.tm.crm.enums.RoleEnum;
import com.tm.crm.repository.CustomerRepository;
import com.tm.crm.util.OAuthHelper;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes=Application.class)
public class CustomerResourceTest {
	
	@Autowired
	WebApplicationContext context;
	
	@Autowired
	private FilterChainProxy springSecurityFilterChain;
    
    private MockMvc mvc;

    @Autowired
    OAuthHelper authHelper;
   
    @Autowired
	CustomerRepository customerRepository;
   
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
			customer.setFirstname("firstname");
			customer.setLastname("lastname");
			customer.setGender(CustomerGender.MALE);
			customer.setPhone("905544655254");
			customer.setCountry(12L);
			customerRepository.save(customer);
		}
	}
    
    @Test
    public void testEchoAnonymous() throws Exception {
        ResultActions resultActions = mvc.perform(get("/customers/echo")).andDo(print());

        resultActions
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void testEchoAuthenticated() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        ResultActions resultActions = mvc.perform(get("/customers/echo").with(bearerToken)).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("echo"));

    }

    @Test
    public void getCustomerByIdSuccesfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        ResultActions resultActions = mvc.perform(get("/customers/"+customer.getId())
        		.with(bearerToken)).andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.email", is(customer.getEmail())))
				.andExpect(jsonPath("$.firstname", is(customer.getFirstname())))
				.andExpect(jsonPath("$.lastname", is(customer.getLastname())))
				.andExpect(jsonPath("$.phone", is(customer.getPhone())))
				.andExpect(jsonPath("$.gender", is(customer.getGender().name())));;
			
    }
    @Test
    public void getCustomerByIdCouldNotFound() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        ResultActions resultActions = mvc.perform(get("/customers/100")
        		.with(bearerToken)).andDo(print());
        
        resultActions
                .andExpect(status().isOk());
        
        Assert.isTrue(resultActions.andReturn().getResponse().getContentAsString().equals(""));
    }
    @Test
    public void getCustomerByEmailSuccesfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        ResultActions resultActions = mvc.perform(post("/customers/email")
        		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        		.param("email", customer.getEmail())
        		.with(bearerToken)).andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.email", is(customer.getEmail())))
				.andExpect(jsonPath("$.firstname", is(customer.getFirstname())))
				.andExpect(jsonPath("$.lastname", is(customer.getLastname())))
				.andExpect(jsonPath("$.phone", is(customer.getPhone())))
				.andExpect(jsonPath("$.gender", is(customer.getGender().name())));
    }
    
    @Test
    public void createCustomerSuccessfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
		Customer newCust = new Customer();
//		newCust.setId(2L);
		newCust.setEmail("user@tm.com2");
		newCust.setPassword("123456");
		newCust.setFirstname("firstname2");
		newCust.setLastname("lastname2");
		newCust.setGender(CustomerGender.FEMALE);
		newCust.setPhone("905544651254");
		newCust.setCountry(12L);
		
        ResultActions resultActions = mvc.perform(post("/customers")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(new ObjectMapper().writeValueAsString(newCust))
        		.with(bearerToken)).andDo(print());
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.email", is(newCust.getEmail())))
				.andExpect(jsonPath("$.firstname", is(newCust.getFirstname())))
				.andExpect(jsonPath("$.lastname", is(newCust.getLastname())))
				.andExpect(jsonPath("$.phone", is(newCust.getPhone())))
				.andExpect(jsonPath("$.gender", is(newCust.getGender().name())));
    }
    
    @Test
    public void updateCustomerSuccessfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        customer.setEmail("user@tm.com3");
        ResultActions resultActions = mvc.perform(put("/customers/"+customer.getId().toString())
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(new ObjectMapper().writeValueAsString(customer))
        		.with(bearerToken)).andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.email", is(customer.getEmail())))
				.andExpect(jsonPath("$.firstname", is(customer.getFirstname())))
				.andExpect(jsonPath("$.lastname", is(customer.getLastname())))
				.andExpect(jsonPath("$.phone", is(customer.getPhone())))
				.andExpect(jsonPath("$.gender", is(customer.getGender().name())));
    }
    
    @Test
    public void searchCustomerByIdSuccesfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        ResultActions resultActions =  mvc.perform(get("/customers")
        		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        		.param("id", customer.getId().toString())
        		.with(bearerToken)).andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", Matchers.hasSize(1)));
    }

    @Test
    public void searchCustomerByEmailSuccesfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        ResultActions resultActions =  mvc.perform(get("/customers")
        		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        		.param("email", customer.getEmail())
        		.with(bearerToken)).andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", Matchers.hasSize(1)));
    }
    @Test
    public void searchCustomerByNameSuccesfull() throws Exception {
    	RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
    	ResultActions resultActions =  mvc.perform(get("/customers")
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.param("name", customer.getFirstname())
    			.with(bearerToken)).andDo(print());
    	resultActions
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    	.andExpect(jsonPath("$.*", Matchers.hasSize(1)));
    }
    @Test
    public void searchCustomerByLastNameSuccesfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        ResultActions resultActions =  mvc.perform(get("/customers")
        		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        		.param("lastname", customer.getLastname())
        		.with(bearerToken)).andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", Matchers.hasSize(1)));
    }
    @Test
    public void searchCustomerByLastNameError() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        ResultActions resultActions =  mvc.perform(get("/customers")
        		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        		.param("lastname", "Xxx")
        		.with(bearerToken)).andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.errorCode", is(1001)));
    }
}