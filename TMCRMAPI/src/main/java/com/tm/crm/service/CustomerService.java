package com.tm.crm.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.crm.entity.Customer;
import com.tm.crm.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	public Customer getCustomerById(long id) {
		return customerRepository.findOne(id);
	}
	
	public Customer getCustomerByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	
	public Customer createCustomer(Customer customer) throws Exception {
		customer.setPassword(new Random(1000).nextInt() + "12345");
		return customerRepository.save(customer);
	}
	
	public void updateBtxCustomer(Customer customer) {
		customerRepository.findOneById(customer.getId()).ifPresent(o -> {
			o.setFirstname(customer.getFirstname());
			o.setLastname(customer.getLastname());
			o.setGender(customer.getGender());
			o.setEmail(customer.getEmail());
			o.setPhone(customer.getPhone());
			o.setMobilePromotion(customer.getMobilePromotion());
			o.setMobilePushMessage(customer.getMobilePushMessage());
			o.setCountry(customer.getCountry());
			customerRepository.save(o);
		});
	}

	public List<Customer> searchByName(String name) {
		return customerRepository.findByFirstname(name);
	}

	public List<Customer> searchByLastname(String lastname) {
		return customerRepository.findByLastname(lastname);
	}
}
