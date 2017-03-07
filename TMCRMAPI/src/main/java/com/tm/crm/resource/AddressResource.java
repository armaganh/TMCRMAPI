package com.tm.crm.resource;

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

import com.tm.crm.entity.CustomerAddress;
import com.tm.crm.exception.messages.TMErrorMessage;
import com.tm.crm.service.AddressService;

@RestController
@RequestMapping("/addresses")
class AddressResource {
	
	@Autowired
	AddressService addressService;
	
	@RequestMapping(value = "/echo", method = RequestMethod.GET)
	public String echo() {
        return "echo";
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CustomerAddress> getAddressById(@PathVariable Long id) {
		
		CustomerAddress address = addressService.getAddressById(id);

		return new ResponseEntity<>(address, HttpStatus.OK);
    }

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<CustomerAddress>> getCustomerByEmail(@RequestParam Long customerId) {
	
		List<CustomerAddress> result = addressService.getCustomerById(customerId);

		return new ResponseEntity<>(result, HttpStatus.OK);
    }
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<CustomerAddress> createCustomer(@RequestBody CustomerAddress address) {
		CustomerAddress btxAddress = null;
		try {
			btxAddress = addressService.createCustomer(address);
		} catch (Exception e) {
			throw new TMErrorMessage(1002, "Address create error!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(btxAddress, HttpStatus.CREATED);
    }
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CustomerAddress> updateAddress(@PathVariable String id,@RequestBody CustomerAddress addr) {
		addr.setId(Long.valueOf(id));
		addressService.updateBtxAddress(addr);
		return new ResponseEntity<>(addr, HttpStatus.OK);
    }
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
		addressService.deleteAddress(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
