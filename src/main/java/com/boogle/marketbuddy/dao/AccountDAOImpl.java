package com.boogle.marketbuddy.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.boogle.marketbuddy.bean.Account;

@Repository
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void updateUserAccount(Account account) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.merge(account);
		transaction.commit();

	}

	@Override
	public Account getUserAccount(String username) {
		//from Named n, Named m where n.name = m.name
		//return account that links to username
		
		
//		return (Account) sessionFactory.openSession().get(Account.class,
//				username);
		
		Query query = sessionFactory.openSession().createQuery("from Account where user.username = :userName ");
		query.setParameter("userName", username);
		List<Account> list = query.list();
		return (Account) list.get(0);
	}

	@Override
	public void deleteUserAccount(String username) {
		Query query = sessionFactory.openSession().createQuery("from Account where user.username = :userName ");
		query.setParameter("userName", username);
		List<Account> list = query.list();
		Account account = (Account) list.get(0);
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(account.getAccountId());
		transaction.commit();

	}

}
