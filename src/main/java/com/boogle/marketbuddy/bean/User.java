package com.boogle.marketbuddy.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Table(name="users")
//@Table(name="tbl_sky",
//uniqueConstraints = {@UniqueConstraint(columnNames={"month", "day"})}
public class User implements Serializable {

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userType=" + userType + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1229255472917151841L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	
	@Id
	@Column(name = "username")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String username;
	@Column(name = "password")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String password;
	@Column(name = "first_name")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String firstName;
	@Column(name = "last_name")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String lastName;
	
	private String userType;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUserId(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}