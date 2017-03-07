package com.tm.crm.config.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.tm.crm.entity.Customer;
import com.tm.crm.enums.RoleEnum;

/**
 * A custom {@link UserDetailsService} where user information
 * is retrieved from a JPA repository
 */
public class CustomUser implements UserDetails {
 
	private static final long serialVersionUID = 1L;
	
	private Customer customer;
	
	public CustomUser(Customer customer) {
		this.customer = customer;
	}
	
	/**
	 * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
	 * @param role the numerical role
	 * @return a collection of {@link GrantedAuthority
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles());
		return authList;
	}
	
	/**
	 * Converts a numerical role to an equivalent list of roles
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles() {
		List<String> roles = new ArrayList<String>();
		roles.add(RoleEnum.ROLE_BOX_OFFICE.name());
		return roles;
	}

	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
 
	public String getPassword() {
		return customer.getPassword();
	}
 
	public String getUsername() {
		return customer.getEmail();
	}
 
	public boolean isAccountNonExpired() {
		return true;
	}
 
	public boolean isAccountNonLocked() {
		return true;
	}
 
	public boolean isCredentialsNonExpired() {
		return true;
	}
 
	public boolean isEnabled() {
		return true;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}