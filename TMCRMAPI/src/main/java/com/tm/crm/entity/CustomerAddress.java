package com.tm.crm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.tm.crm.enums.AddressType;

@Entity
@Table(name="CUSTOMER_ADDRESSES")
public class CustomerAddress implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="ID")
	private Long id;

	@Column(name="ISDEFAULT")
	private Boolean isdefault;

	@NotNull
	@Column(name = "BTX_CUSTOMER_ID")
	private Long customerId;

	@Column(name = "TITLE")
	private String title = "";

	@Enumerated(EnumType.STRING)
	@Column(name="TYPE")
	private AddressType type = AddressType.OTHER;


	@Column(name = "STREET")
	private String street = "";

	@Column(name = "HOUSE_NUMBER")
	private String houseNumber;

	@Column(name = "FLOOR_NUMBER")
	private String floorNumber;

	@Column(name = "APARTMENT_NUMBER")
	private String apartmentNumber;

	@Column(name = "POBOX")
	private String poBox;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "TOWN")
	private String town;

	@Column(name = "PROVINCE")
	private String province;

	@Column(name = "CITY")
	private String city;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "ADDITIONAL_PHONE")
	private String additionalPhone;

	@Column(name = "DELIVERY_COMMENTS")
	private String deliveryComments;

	@Column(name = "BTX_COUNTRY_ID")
	private Long  country;

	@Transient
	private String phoneCountryCode="";

	@Transient
	private String phoneNumber="";

	@Transient
	private String additionalPhoneNumber="";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Boolean isdefault) {
		this.isdefault = isdefault;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public AddressType getType() {
		return type;
	}

	public void setType(AddressType type) {
		this.type = type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(String apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public String getPoBox() {
		return poBox;
	}

	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAdditionalPhone() {
		return additionalPhone;
	}

	public void setAdditionalPhone(String additionalPhone) {
		this.additionalPhone = additionalPhone;
	}

	public String getDeliveryComments() {
		return deliveryComments;
	}

	public void setDeliveryComments(String deliveryComments) {
		this.deliveryComments = deliveryComments;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public String getPhoneCountryCode() {
		return phoneCountryCode;
	}

	public void setPhoneCountryCode(String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
		updatePhone();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		updatePhone();
	}

	public String getAdditionalPhoneNumber() {
		return additionalPhoneNumber;
	}

	public void setAdditionalPhoneNumber(String additionalPhoneNumber) {
		this.additionalPhoneNumber = additionalPhoneNumber;
		updatePhone();
	}
	
	private void updatePhone() {
		if(	((phoneCountryCode != null) && (phoneCountryCode.length() > 0))//
				//				&&((phoneAreaCode != null) && (phoneAreaCode.length() == 3))//
				&&((phoneNumber != null) && (phoneNumber.length() > 7)) )
		{
			phone = phoneCountryCode + /*phoneAreaCode +*/ phoneNumber;
		}
		if(	((phoneCountryCode != null) && (phoneCountryCode.length() > 0))//
				//				&&((phoneAreaCode != null) && (phoneAreaCode.length() == 3))//
				&&((additionalPhoneNumber != null) && (additionalPhoneNumber.length() > 7)) )
		{
			additionalPhone = phoneCountryCode + /*phoneAreaCode +*/ additionalPhoneNumber;
		}
	}
}