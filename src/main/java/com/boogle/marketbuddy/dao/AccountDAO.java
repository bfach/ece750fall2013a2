package com.boogle.marketbuddy.dao;

import com.boogle.marketbuddy.bean.Account;

public interface AccountDAO {

	
	public void updateUserAccount(Account account);
	
	public Account getUserAccount(String username);
	
	public void deleteUserAccount(String username);
}
