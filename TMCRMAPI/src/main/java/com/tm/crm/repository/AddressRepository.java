package com.tm.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tm.crm.entity.CustomerAddress;


public interface AddressRepository extends JpaRepository<CustomerAddress, Long> {
	 Optional<CustomerAddress> findOneById(Long id);
	 List<CustomerAddress> findByCustomerId(Long customerId);
}