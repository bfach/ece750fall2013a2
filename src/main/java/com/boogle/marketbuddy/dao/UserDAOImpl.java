package com.boogle.marketbuddy.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.boogle.marketbuddy.bean.User;
 
@Repository
public class UserDAOImpl implements UserDAO {
 
	@Autowired
    private SessionFactory sessionFactory;
 
//    public UserDAOImpl() {
//        sessionFactory = HibernateUtil.sessionFactory();
//    }
 
    public void saveUser(User user) {
 
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(user);
        transaction.commit();
 
    }
 
    public User findUserByName(String userName) {
 
        return (User) sessionFactory.openSession().get(User.class, userName);
 
    }
 
    public void deleteUser(User user) {
 
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
    }
}