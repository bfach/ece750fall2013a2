package com.boogle.marketbuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boogle.marketbuddy.bean.Account;
import com.boogle.marketbuddy.bean.User;
import com.boogle.marketbuddy.dao.AccountDAO;
import com.boogle.marketbuddy.dao.UserDAO;

@Controller
public class AccountController {
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	AccountDAO accountDAO;
	
	@RequestMapping(value = "/users/accounts/{userName}", method = RequestMethod.POST)
	@ResponseBody
	public String addAccount(@PathVariable String userName, @RequestBody Account account) {
		User user = userDAO.findUserByName(userName);

		if(user == null){
			return "ERROR adding account to non-existant user";
		}
		account.setUser(user);
		accountDAO.updateUserAccount(account);
		
		return "User " + account.toString() +" added";
	}
	
	@RequestMapping(value = "/users/accounts/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public Account getUser(@PathVariable String userName) {
		return accountDAO.getUserAccount(userName);
	}

	@RequestMapping(value = "/users/accounts/{userName}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String userName) {
		Account account = accountDAO.getUserAccount(userName);
		
		if(account == null){
			System.err.println("User account does not exist to delete, aborting ");
			return;
		}
		
		accountDAO.deleteUserAccount(userName);
	}
	
}
