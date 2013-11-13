package com.boogle.marketbuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boogle.marketbuddy.bean.User;
import com.boogle.marketbuddy.dao.UserDAO;

@Controller
public class AccessController {

	@Autowired
	UserDAO userDAO;
	
	
	@RequestMapping(value = "/login/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public boolean login(@PathVariable String userName) {
		User user = userDAO.findUserByName(userName);
		//user exists?
		return user == null ? false : true;
	}
	
	@RequestMapping(value = "/logout/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public boolean logout(@PathVariable String userName) {
		User user = userDAO.findUserByName(userName);
		//user exists?
		return user == null ? false : true;
	}
	
}
