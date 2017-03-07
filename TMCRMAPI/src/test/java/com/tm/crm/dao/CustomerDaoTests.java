package com.tm.crm.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.tm.crm.Application;
import com.tm.crm.entity.Customer;
import com.tm.crm.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan(basePackages="com.tm.crm.entity")
@ComponentScan(basePackages="com.tm.crm.dao") 
@SpringBootTest(classes=Application.class)
public class CustomerDaoTests {

    @Autowired
    private EntityManager entityManager;
  
	@Autowired
	private CustomerRepository customerRepository;

    @Test
    public void createCustomerSuccesfull() {
    	
    	Customer customer = new Customer();
    	customer.setId(1L);
    	customer.setEmail("testuser@test.com");
    	customer.setFirstname("TEST USER");
    	customer.setLastname("TEST SURNAME");
    	customer.setPassword("1234");
      	customer = customerRepository.save(customer);

    	Customer result = entityManager.find(Customer.class, customer.getId());
    	Assert.isTrue(result.equals(customer));
    }
    @Test(expected=javax.validation.ConstraintViolationException.class)
    public void createCustomerNullEmailError() {
    	Customer customer = new Customer();
    	customer.setId(2L);
    	customer.setFirstname("TEST USER");
    	customer.setLastname("TEST SURNAME");
    	customer.setPassword("1234");
    	customerRepository.saveAndFlush(customer);
    }
    @Test
    public void updateCustomerSuccesfull() {
    	Customer customer = new Customer();
    	customer.setId(3L);
    	customer.setEmail("testuser@test.com");
    	customer.setFirstname("TEST USER");
    	customer.setLastname("TEST SURNAME");
    	customer.setPassword("1234");
    	customerRepository.save(customer);
    	
    	customer.setEmail("testuser2@test.com");
    	customer.setFirstname("TEST USER2");
    	customer.setLastname("TEST SURNAME2");
    	customer.setPassword("12345");
    	customerRepository.save(customer);
    	
    	Customer result = entityManager.find(Customer.class, customer.getId());
    	Assert.isTrue(result.getFirstname().equals(customer.getFirstname()));
    	Assert.isTrue(result.getLastname().equals(customer.getLastname()));
    	Assert.isTrue(result.getPassword().equals(customer.getPassword()));
    	
    }
    @Test(expected=javax.validation.ConstraintViolationException.class)
    public void updateCustomerNullEmailError() {
    	Customer customer = new Customer();
    	customer.setId(4L);
    	customer.setEmail("testuser2s@test.com");
    	customer.setFirstname("TEST USER");
    	customer.setLastname("TEST SURNAME");
    	customer.setPassword("1234");
    	customerRepository.save(customer);
    	
    	customer.setEmail(null);
    	customerRepository.saveAndFlush(customer);
    }
    
    @Test
    public void findCustomerById() {
    	Customer customer = new Customer();
    	customer.setId(5L);
    	customer.setEmail("testuser5@test.com");
    	customer.setFirstname("TEST USER");
    	customer.setLastname("TEST SURNAME");
    	customer.setPassword("1234");
    	customer = customerRepository.save(customer);
    	
    	Customer result = customerRepository.findOne(5L);
    	Assert.isTrue(result.equals(customer));
    }
    @Test
    public void findErrorCustomerById() {
    	Customer result = customerRepository.findOne(8L);
    	Assert.isTrue(result == null);
    }
    @Test
    public void findCustomerByEmail() {
    	Customer customer = new Customer();
    	customer.setId(6L);
    	customer.setEmail("emailSearch@test.com");
    	customer.setFirstname("TEST USER");
    	customer.setLastname("TEST SURNAME");
    	customer.setPassword("1234");
    	customer = customerRepository.save(customer);
    	
    	Customer result = customerRepository.findByEmail("emailSearch@test.com");
    	Assert.isTrue(result.equals(customer));
    }
    @Test
    public void findErrorCustomerByEmail() {
      	Customer result = customerRepository.findByEmail("XXXXX@test.com");
      	Assert.isTrue(result == null);
    }
    @Test
    public void searchCustomerByEmail() {
    	Customer customer = new Customer();
    	customer.setId(7L);
    	customer.setEmail("testuserEmail@teste.com");
    	customer.setFirstname("TEST USER");
    	customer.setLastname("TEST SURNAME");
    	customer.setPassword("1234");
      	customer = customerRepository.save(customer);
      	List<Customer> result = new ArrayList<Customer>();
      	result.add(customerRepository.findByEmail("testuserEmail@teste.com"));
      	Assert.isTrue(result.size() == 1);
    }
    @Test
    public void searchCustomerId() {
    	Customer customer = new Customer();
    	customer.setId(8L);
    	customer.setEmail("testuserId@teste.com");
    	customer.setFirstname("TEST USER");
    	customer.setLastname("TEST SURNAME");
    	customer.setPassword("1234");
      	customer = customerRepository.save(customer);
      	List<Customer> result = new ArrayList<Customer>();
      	result.add(customerRepository.findByEmail("testuserId@teste.com"));
      	Assert.isTrue(result.size() == 1);
    }
    @Test
    public void searchCustomerByName() {
    	Customer customer = new Customer();
    	customer.setId(9L);
    	customer.setEmail("firstname1@teste.com");
    	customer.setFirstname("firstname");
    	customer.setLastname("TEST SURNAME");
    	customer.setPassword("1234");
      	customer = customerRepository.save(customer);
      	
      	Customer customer2 = new Customer();
    	customer2.setId(10L);
    	customer2.setEmail("firstname2@teste.com");
    	customer2.setFirstname("firstname");
    	customer2.setLastname("TEST SURNAME");
    	customer2.setPassword("1234");
      	customerRepository.saveAndFlush(customer2);

    	List<Customer> result = customerRepository.findByFirstname("firstname");
    	Assert.isTrue(result.size() == 2);
    	for (Customer btxCustomer : result) {
    		Assert.isTrue(btxCustomer.getFirstname().equals("firstname"));
		}
    }
    @Test
    public void searchCustomerBySurname() {
    	Customer customer = new Customer();
    	customer.setId(11L);
    	customer.setEmail("firstname3@teste.com");
    	customer.setFirstname("firstname s");
    	customer.setLastname("lastname");
    	customer.setPassword("1234");
      	customer = customerRepository.save(customer);
      	
      	Customer customer2 = new Customer();
    	customer2.setId(10L);
    	customer2.setEmail("firstname4@teste.com");
    	customer2.setFirstname("firstname s");
    	customer2.setLastname("lastname");
    	customer2.setPassword("1234");
      	customerRepository.saveAndFlush(customer2);

    	List<Customer> result = customerRepository.findByLastname("lastname");
    	Assert.isTrue(result.size() == 2);
    	for (Customer btxCustomer : result) {
    		Assert.isTrue(btxCustomer.getLastname().equals("lastname"));
		}
    }
}