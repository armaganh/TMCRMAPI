package com.tm.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tm.crm.entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
	 Optional<Customer> findOneById(Long id);
	 Customer findByEmail(String email);
	 List<Customer> findByFirstname(String name);
	 List<Customer> findByLastname(String lastName);
}