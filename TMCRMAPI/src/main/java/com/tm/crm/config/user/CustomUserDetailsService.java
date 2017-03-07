package com.tm.crm.config.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tm.crm.entity.Customer;
import com.tm.crm.repository.CustomerRepository;


/**
 * A custom {@link UserDetailsService} where user information
 * is retrieved from a JPA repository
 */

@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerDao;

	/**
	 * Returns a populated {@link UserDetails} object. 
	 * The username is first retrieved from the database and then mapped to 
	 * a {@link UserDetails} object.
	 */
	public UserDetails loadUserByUsername(String param) throws UsernameNotFoundException {
		try {
			
			Customer domainUser = customerDao.findByEmail(param);
			
			if(domainUser == null){
				throw new UsernameNotFoundException("UserName " + param + " not found");
			}
			
			return new CustomUser(domainUser);

		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage());
		}
	}
}