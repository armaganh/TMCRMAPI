package com.tm.crm.rest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.tm.crm.entity.CustomerAddress;
import com.tm.crm.enums.AddressType;
import com.tm.crm.enums.CustomerGender;
import com.tm.crm.enums.RoleEnum;
import com.tm.crm.repository.AddressRepository;
import com.tm.crm.repository.CustomerRepository;
import com.tm.crm.util.OAuthHelper;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes=Application.class)
public class AddressResourceTest {
	
	@Autowired
	WebApplicationContext context;
	
	@Autowired
	private FilterChainProxy springSecurityFilterChain;
    
    private MockMvc mvc;

    @Autowired
    OAuthHelper authHelper;
   
    @Autowired
	CustomerRepository customerRepository;
   
    
    @Autowired
	AddressRepository addressRepository;
   
    private static Customer customer;
   
    private static CustomerAddress address;

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
			customerRepository.flush();
		}
		if(address == null) {
			address = new CustomerAddress();
	    	address.setIsdefault(true);
	    	address.setCustomerId(customer.getId());
	    	address.setType(AddressType.MAIN);
	    	address.setStreet("street");
	    	address.setHouseNumber("house number");
	    	address.setFloorNumber("floor Number");
	    	address.setApartmentNumber("apartman number");
	    	address.setPoBox("Po box");
	    	address.setZipcode("zipCode");
	    	address.setTown("town");
	    	address.setProvince("province");
	    	address.setCity("city");
	    	address.setPhone("9721111111111");
	    	address.setAdditionalPhone("9723234334343");
	    	address.setDeliveryComments("delivery comments");
	    	address.setCountry(1L);
	    	addressRepository.save(address);
	    	addressRepository.flush();
		}
	}
    
    @Test
    public void testEchoAnonymous() throws Exception {
        ResultActions resultActions = mvc.perform(get("/addresses/echo")).andDo(print());

        resultActions
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void testEchoAuthenticated() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        ResultActions resultActions = mvc.perform(get("/addresses/echo").with(bearerToken)).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("echo"));

    }

    @Test
    public void getAddressByCustomerIdSuccesfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        ResultActions resultActions = mvc.perform(get("/addresses")
        		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        		.param("customerId", customer.getId().toString())
        		.with(bearerToken)).andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.*", Matchers.hasSize(2)));
			
    }
    
    @Test
    public void getAddressByIdSuccesfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        ResultActions resultActions = mvc.perform(get("/addresses/"+address.getId())
        		.with(bearerToken)).andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.customerId").value(address.getCustomerId()))
				.andExpect(jsonPath("$.id").value(address.getId()))
				.andExpect(jsonPath("$.type").value(address.getType().name()))
				.andExpect(jsonPath("$.houseNumber", is(address.getHouseNumber())))
				.andExpect(jsonPath("$.floorNumber", is(address.getFloorNumber())));			
    }
    @Test
    public void createAddressSuccessfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
    	
        CustomerAddress address2 = new CustomerAddress();
    	address2.setCustomerId(customer.getId());
    	address2.setIsdefault(true);
    	address2.setType(AddressType.SECONDARY);
    	address2.setStreet("street2");
    	address2.setHouseNumber("house number2");
    	address2.setFloorNumber("floor Number2");
    	address2.setApartmentNumber("apartman number");
    	address2.setPoBox("Po box");
    	address2.setZipcode("zipCode");
    	address2.setTown("town");
    	address2.setProvince("province");
    	address2.setCity("city");
    	address2.setPhone("9721111111111");
    	address2.setAdditionalPhone("9723234334343");
    	address2.setDeliveryComments("delivery comments");
    	address2.setCountry(1L);
		
        ResultActions resultActions = mvc.perform(post("/addresses")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(new ObjectMapper().writeValueAsString(address2))
        		.with(bearerToken)).andDo(print());
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.customerId").value(address2.getCustomerId()))
				.andExpect(jsonPath("$.street", is(address2.getStreet())))
				.andExpect(jsonPath("$.houseNumber", is(address2.getHouseNumber())))
				.andExpect(jsonPath("$.floorNumber", is(address2.getFloorNumber())))
        		.andExpect(jsonPath("$.city", is(address2.getCity())));	
    }
    
    @Test
    public void updateAddressSuccessfull() throws Exception {
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        address.setStreet("update street");
        ResultActions resultActions = mvc.perform(put("/addresses/"+address.getId().toString())
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(new ObjectMapper().writeValueAsString(address))
        		.with(bearerToken)).andDo(print());
        resultActions
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.customerId").value(address.getCustomerId()))
			.andExpect(jsonPath("$.id").value(address.getId()))
			.andExpect(jsonPath("$.street", is(address.getStreet())))
			.andExpect(jsonPath("$.houseNumber", is(address.getHouseNumber())))
			.andExpect(jsonPath("$.floorNumber", is(address.getFloorNumber())))
			.andExpect(jsonPath("$.city", is(address.getCity())));
        
        Assert.isTrue(addressRepository.findOneById(address.getId()).isPresent());
        CustomerAddress adr =  addressRepository.findOne(address.getId());
        Assert.isTrue(adr.getStreet().equals(address.getStreet()));
    }
    @Test
    public void deleteAddressSuccessfull() throws Exception {
    	 CustomerAddress address2 = new CustomerAddress();
     	address2.setCustomerId(customer.getId());
     	address2.setIsdefault(true);
     	address2.setType(AddressType.SECONDARY);
     	address2.setStreet("street2");
     	address2.setHouseNumber("house number2");
     	address2.setFloorNumber("floor Number2");
     	address2.setApartmentNumber("apartman number");
     	address2.setPoBox("Po box");
     	address2.setZipcode("zipCode");
     	address2.setTown("town");
     	address2.setProvince("province");
     	address2.setCity("city");
     	address2.setPhone("9721111111111");
     	address2.setAdditionalPhone("9723234334343");
     	address2.setDeliveryComments("delivery comments");
     	address2.setCountry(1L);
     	addressRepository.save(address2);
        RequestPostProcessor bearerToken = authHelper.addBearerToken("testuser", RoleEnum.ROLE_BOX_OFFICE.name());
        address.setStreet("update street");
        ResultActions resultActions = mvc.perform(delete("/addresses/"+address2.getId().toString())
        		.contentType(MediaType.APPLICATION_JSON)
        		.with(bearerToken)).andDo(print());
        resultActions
	        .andExpect(status().isNoContent());
        
    	Assert.isTrue(!addressRepository.findOneById(address2.getId()).isPresent());
    }
   
}