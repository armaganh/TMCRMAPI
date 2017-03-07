package com.tm.crm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tm.crm.enums.CustomerGender;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name="CUSTOMER")
public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="ID")
	private Long id;
	
	@Column(name="EMAIL")
	@Email
	@NotNull
	private String email;

	@Column(name="FNAME")
	private String firstname;

	@Column(name="LNAME")
	private String lastname;

	@Enumerated(EnumType.STRING)
	@Column(name="GENDER")
	private CustomerGender gender;

	@Column(name="MOBILE_PHONE")
	private String phone;
	
	@Column(name="hashedp")
	@NotNull
	@JsonIgnore
	private String password;

	@Column(name = "BTX_COUNTRY_ID")
	private Long  country;
	
	@Column(name="MOBILEPROMOTION")
	private Boolean mobilePromotion=Boolean.TRUE;

	@Column(name="MOBILEPUSHMESSAGE")
	private Boolean mobilePushMessage=Boolean.FALSE;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATEDATE")
	private Date createDate=new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EDITDATE")
	private Date editDate=new Date();

	@Temporal(TemporalType.DATE)
	@Column(name="BIRTHDATE")
	private Date birthdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public Long getCountry() {
		return country;
	}

	public CustomerGender getGender() {
		return gender;
	}

	public void setGender(CustomerGender gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getMobilePromotion() {
		return mobilePromotion;
	}

	public void setMobilePromotion(Boolean mobilePromotion) {
		this.mobilePromotion = mobilePromotion;
	}

	public Boolean getMobilePushMessage() {
		return mobilePushMessage;
	}

	public void setMobilePushMessage(Boolean mobilePushMessage) {
		this.mobilePushMessage = mobilePushMessage;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
}
