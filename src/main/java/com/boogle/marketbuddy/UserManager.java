package com.boogle.marketbuddy;

import com.boogle.marketbuddy.bean.User;
import com.boogle.marketbuddy.dao.UserDAO;
import com.boogle.marketbuddy.dao.UserDAOImpl;

public class UserManager {
	 
    public static void main(String[] args) {
 
        UserDAO userDao = new UserDAOImpl();
 
        // create new user
        User user = new User();
        user.setUserId("blah");
        user.setFirstName("fname01");
        user.setLastName("lname01");
 
        userDao.saveUser(user);
 
        // find user by name
        User fromDB = userDao.findUserByName("blah");
        System.out.println(fromDB);
 
        assert fromDB != null;
 
        // delete user
        userDao.deleteUser(fromDB);
 
    }
}