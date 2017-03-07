package com.tm.crm.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
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
import com.tm.crm.entity.CustomerAddress;
import com.tm.crm.enums.AddressType;
import com.tm.crm.enums.CustomerGender;
import com.tm.crm.repository.AddressRepository;
import com.tm.crm.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan(basePackages="com.tm.crm.entity")
@ComponentScan(basePackages="com.tm.crm.dao") 
@SpringBootTest(classes=Application.class)
public class AdressDaoTests {

    @Autowired
    private EntityManager entityManager;
  
	@Autowired
	private AddressRepository addressRepository;
	
    @Autowired
	CustomerRepository customerRepository;
    
    private Customer customer;

	 @Before
	public void setUp() {
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
    public void createAddressSuccesfull() {

    	CustomerAddress address = new CustomerAddress();
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
    	   	
    	address = addressRepository.save(address);

    	CustomerAddress result = entityManager.find(CustomerAddress.class, address.getId());
    	Assert.isTrue(result.equals(address));
    }
    @Test(expected=javax.validation.ConstraintViolationException.class)
    public void createAddressNullCustError() {
    	CustomerAddress address = new CustomerAddress();
    	address.setIsdefault(true);
    	address.setCustomerId(null);
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
    	   	
    	addressRepository.saveAndFlush(address);
    }
    @Test
    public void updateCustomerSuccesfull() {
    	CustomerAddress address = new CustomerAddress();
    	address.setCustomerId(customer.getId());
    	address.setIsdefault(true);
    	address.setCustomerId(customer.getId());
    	address.setType(AddressType.SECONDARY);
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
    	   	
    	address = addressRepository.save(address);
    	
    	address.setPoBox("Po box2");
    	address.setZipcode("zipCode2");
    	address.setTown("town2");
    	address.setProvince("province2");

    	address = addressRepository.save(address);

    	CustomerAddress result = entityManager.find(CustomerAddress.class, address.getId());
    	Assert.isTrue(result.equals(address));
    	
    }

    @Test
    public void findCustomerById() {
    	
    	Customer cust = new Customer();
		cust.setId(2L);
		cust.setEmail("user2@tm.com");
		cust.setPassword("123456");
		cust.setFirstname("firstname");
		cust.setLastname("lastname");
		cust.setGender(CustomerGender.MALE);
		cust.setPhone("905544655254");
		cust.setCountry(12L);
		customerRepository.save(cust);
    	   	
    	CustomerAddress address = new CustomerAddress();
    	address.setCustomerId(cust.getId());
    	address.setIsdefault(true);
    	address.setCustomerId(cust.getId());
    	address.setType(AddressType.SECONDARY);
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
    
    	address = addressRepository.save(address);

    	CustomerAddress address2 = new CustomerAddress();
    	address2.setCustomerId(cust.getId());
    	address2.setIsdefault(true);
    	address2.setCustomerId(cust.getId());
    	address2.setType(AddressType.SECONDARY);
    	address2.setStreet("street");
    	address2.setHouseNumber("house number");
    	address2.setFloorNumber("floor Number");
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
    	   	
    	address2 = addressRepository.save(address2);
    	
    	List<CustomerAddress> addressList = addressRepository.findByCustomerId(cust.getId());
    	
    	Assert.isTrue(addressList.size() == 2);
    	Assert.isTrue(addressList.get(0).equals(address) || addressList.get(1).equals(address));
    	Assert.isTrue(addressList.get(0).equals(address2) || addressList.get(1).equals(address2));
    }
    @Test
    public void findErrorCustomerById() {
    	List<CustomerAddress> addressList = addressRepository.findByCustomerId(8L);
    	Assert.isTrue(addressList.size() == 0);
    }
    @Test
    public void findAddressById() {
    	CustomerAddress address = new CustomerAddress();
    	address.setCustomerId(customer.getId());
    	address.setIsdefault(true);
    	address.setCustomerId(customer.getId());
    	address.setType(AddressType.OTHER);
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
    	   	
    	address = addressRepository.save(address);
    	
    	Assert.isTrue(addressRepository.findOneById(address.getId()).isPresent());
    }
    @Test
    public void deleteAdress() {
    	Customer cust = new Customer();
		cust.setId(2L);
		cust.setEmail("user2@tm.com");
		cust.setPassword("123456");
		cust.setFirstname("firstname");
		cust.setLastname("lastname");
		cust.setGender(CustomerGender.MALE);
		cust.setPhone("905544655254");
		cust.setCountry(12L);
		customerRepository.save(cust);
    	   	
    	CustomerAddress address = new CustomerAddress();
    	address.setCustomerId(cust.getId());
    	address.setIsdefault(true);
    	address.setCustomerId(cust.getId());
    	address.setType(AddressType.SECONDARY);
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
    
    	address = addressRepository.save(address);
    	
    	addressRepository.delete(address);
    	Assert.isTrue(!addressRepository.findOneById(address.getId()).isPresent());
    }
}