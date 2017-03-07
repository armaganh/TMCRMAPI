package com.tm.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.crm.entity.CustomerAddress;
import com.tm.crm.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepository;

	public List<CustomerAddress> getCustomerById(long customerId) {
		return addressRepository.findByCustomerId(customerId);
	}

	public CustomerAddress getAddressById(Long id) {
		return addressRepository.findOne(id);
	}

	public CustomerAddress createCustomer(CustomerAddress address) throws Exception {
		return addressRepository.save(address);
	}

	public void updateBtxAddress(CustomerAddress address) {
		addressRepository.findOneById(address.getId()).ifPresent(o -> {
			o.setIsdefault(address.getIsdefault());
			o.setType(address.getType());
			o.setStreet(address.getStreet());
			o.setHouseNumber(address.getHouseNumber());
			o.setFloorNumber(address.getFloorNumber());
			o.setApartmentNumber(address.getApartmentNumber());
			o.setPoBox(address.getPoBox());
			o.setZipcode(address.getZipcode());
			o.setTown(address.getTown());
			o.setProvince(address.getProvince());
			o.setCity(address.getCity());
			o.setPhone(address.getPhone());
			o.setAdditionalPhone(address.getAdditionalPhone());
			o.setDeliveryComments(address.getDeliveryComments());
			o.setCountry(address.getCountry());
			addressRepository.save(o);
		});
	}

	public void deleteAddress(Long id) {
		addressRepository.delete(id);
	}
}
