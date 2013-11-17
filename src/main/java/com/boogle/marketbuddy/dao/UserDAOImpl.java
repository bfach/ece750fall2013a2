package com.boogle.marketbuddy.dao;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.boogle.marketbuddy.bean.User;
 
@Repository
public class UserDAOImpl implements UserDAO {
 
	@Autowired
    private SessionFactory sessionFactory;
 
	//@Transactional(propagation = Propagation.REQUIRED)
	public void saveUser(User user) {
        Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.merge(user);
	        
			transaction.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			transaction.rollback();
			
		} finally{
			session.close();
		}
    }
 
	//@Transactional(propagation = Propagation.REQUIRED)
	public User findUserByName(String userName) {
		Session session = sessionFactory.openSession();
		
        try{
        	return (User) session.get(User.class, userName);
        }finally{
        	session.close();
        }
 
    }
 
	//@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(User user) {
        Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.delete(user);
	        
			transaction.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			transaction.rollback();
			
		} finally{
			session.close();
		}

	
	}
    
    public int retrieveUserId(String username) {
		final User user = findUserByName(username);
		
		if(user == null){
			throw new IllegalArgumentException("User not registered " + username);
		}
		return user.getId();
	}

}