package com.boogle.marketbuddy.dao;

import com.boogle.marketbuddy.bean.User;

public interface UserDAO {

	void saveUser(User user);
	User findUserByName(String userName);
	void deleteUser(User user);
	int retrieveUserId(String username);
}
