package com.boogle.marketbuddy.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.boogle.marketbuddy.bean.Account;
import com.boogle.marketbuddy.bean.Portfolio;

@Repository
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void updateUserAccount(Account account) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.merge(account);
			transaction.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			transaction.rollback();
			
		} finally{
			session.close();
		}

	}
	
	
	@Override
	//@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Account getUserAccount(String username) {
		//from Named n, Named m where n.name = m.name
		//return account that links to username
		Session session = sessionFactory.openSession();
		
        try{
        	Query query = session.createQuery("from Account where user.username = :userName ");
    		query.setParameter("userName", username);
    		List<Account> list = query.list();
    		return (Account) list.get(0);
    		//session.clear();
        }finally{
        	session.close();
        }

	}

	@Override
	//@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteUserAccount(String username) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		Query query = session.createQuery("from Account where user.username = :userName ");
		query.setParameter("userName", username);
		List<Account> list = query.list();
		Account account = (Account) list.get(0);
		
		try {
			transaction = session.beginTransaction();
			session.delete(account.getAccountId());
			transaction.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			transaction.rollback();
			
		} finally{
			session.close();
		}

	}

}
