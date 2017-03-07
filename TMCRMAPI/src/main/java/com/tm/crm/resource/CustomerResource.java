package com.tm.crm.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.crm.entity.Customer;
import com.tm.crm.exception.messages.TMErrorMessage;
import com.tm.crm.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customers")
class CustomerResource {
	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = "/echo", method = RequestMethod.GET)
	public String echo() {
        return "echo";
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		
		Customer btxCustomer = customerService.getCustomerById(id);
		
		return new ResponseEntity<>(btxCustomer, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
		Customer btxCustomer = customerService.getCustomerByEmail(email);
		
		return new ResponseEntity<>(btxCustomer, HttpStatus.OK);
    }
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Customer>> searchById(@RequestParam(required=false) Long id, @RequestParam(required=false) String name, @RequestParam(required=false) String email, @RequestParam(required=false) String lastname) {

		List<Customer> result = new ArrayList<>();
		if(id != null) {
			result.add(customerService.getCustomerById(id));			
		} else if(name != null) {
			result.addAll(customerService.searchByName(name));
		} else if(email != null) {
			result.add(customerService.getCustomerByEmail(email));
		} else if(lastname != null) {
			result.addAll(customerService.searchByLastname(lastname));
		}
		if(result.isEmpty()) {
			throw new TMErrorMessage(1001, "No data found!", HttpStatus.OK);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);

    }
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer cust) {
		log.debug("createCustomer start");
		Customer btxCustomer = null;
		try {
			btxCustomer = customerService.createCustomer(cust);
			log.info("Customer Create Succesfully CustId: {}, CustEmail: {}", cust.getId(), cust.getEmail());
		} catch (Exception e) {
			log.error("Create Customer Error :{}", e.getMessage());
			throw new TMErrorMessage(1002, "Customer create error!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.debug("createCustomer finish");
		return new ResponseEntity<>(btxCustomer, HttpStatus.CREATED);
    }
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Customer> updateCustomer(@PathVariable String id,@RequestBody Customer cust) {
		log.debug("updateCustomer start");
		cust.setId(Long.valueOf(id));
		customerService.updateBtxCustomer(cust);
		log.info("Customer Update Succesfully CustId: {}, CustEmail: {}", cust.getId(), cust.getEmail());
		log.debug("updateCustomer finish");
		return new ResponseEntity<>(cust, HttpStatus.OK);
    }
}
