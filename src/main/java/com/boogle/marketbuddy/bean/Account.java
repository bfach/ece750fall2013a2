package com.boogle.marketbuddy.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5553009170923083124L;

	@Id
	private String accountId;
	
	private double balance;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance
				+ ", user=" + user.toString() + "]";
	}
	
}
