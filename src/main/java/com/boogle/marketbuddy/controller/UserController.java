package com.boogle.marketbuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boogle.marketbuddy.bean.User;
import com.boogle.marketbuddy.dao.UserDAO;

@Controller
public class UserController {
	@Autowired
	private UserDAO dao;

	@RequestMapping(value = "/users/{userName}", method = RequestMethod.POST)
	@ResponseBody
	public String addUser(@PathVariable String userName, @RequestBody User user) {
		//use json if there
		if(user.getUsername() == null){
			user.setUserId(userName);
		}
		
		try {
			dao.saveUser(user);
		} catch (Exception e) {
			return e.getMessage().toString();
		}
		
		return "User " + user.toString() +" added";
	}
	
	@RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(@PathVariable String userName) {
		return dao.findUserByName(userName);
	}

	@RequestMapping(value = "/users/{userName}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String userName) {
		User user = dao.findUserByName(userName);
		
		if(user == null){
			System.err.println("User does not exist to delete, aborting ");
			return;
		}
		
		dao.deleteUser(user);
	}
}
