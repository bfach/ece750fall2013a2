package com.boogle.marketbuddy.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.boogle.marketbuddy.bean.Portfolio;
import com.boogle.marketbuddy.bean.Share;
import com.boogle.marketbuddy.bean.Stock;
import com.boogle.marketbuddy.bean.TradeOrder;
import com.boogle.marketbuddy.bean.User;

@Repository
//@Transactional
public class PortfolioDAOImpl implements PortfolioDAO {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger LOG = LoggerFactory.getLogger(PortfolioDAOImpl.class);
	 
	@Override
	public void writePortfolio(Portfolio portfolio, String username) {
		portfolio.setUserId(userDAO.retrieveUserId(username));
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
//		for(Stock stock : portfolio.getStocks()){
//			stock.setPortfolioId(portfolio.getPortfolioId());
//		}
		session.clear();
		
		try {
			transaction = session.beginTransaction();
			session.merge(portfolio);
			transaction.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			LOG.error("Writing of portfolio failed! ", e);
			//e.printStackTrace();
			transaction.rollback();
			throw e;
			
		} finally{
			session.close();
		}
	}


	@Override
	public Portfolio retrievePortfolio(String username) {
		Session session = sessionFactory.openSession();
		
        try{
        	User user = (User) session.get(User.class, username);
        	if(user == null){
        		throw new IllegalArgumentException("User does not exist");
        	}
        	
        	Portfolio portfolio = (Portfolio) session.get(Portfolio.class,
    				user.getId());
    		//session.clear();
        	
        	if(portfolio == null){
        		return null;
        	}
        	
        	Hibernate.initialize(portfolio.getStocks());
        
        	Query query = session.createQuery("from Stock s where s.portfolioId = :portfolioId ");
        	query.setParameter("portfolioId", portfolio.getPortfolioId());
        	List<Stock> list = query.list();
        	
        	portfolio.getStocks().addAll(list);
        	
        	return portfolio;
        }finally{
        	session.close();
        }

	}

	@Override
	public void deletePortfolio(String username) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
        try{
        	User user = (User) session.get(User.class, username);
        	if(user == null){
        		throw new IllegalArgumentException("User does not exist");
        	}
        	
        	Portfolio portfolio = (Portfolio) session.get(Portfolio.class,
    				user.getId());
    		//session.clear();
        	
        	
        	Hibernate.initialize(portfolio.getStocks());
        
        	Query query = session.createQuery("from Stock s where s.portfolioId = :portfolioId ");
        	query.setParameter("portfolioId", portfolio.getPortfolioId());
        	List<Stock> list = query.list();
        	
        	portfolio.getStocks().addAll(list);
        	transaction = session.beginTransaction();
        	session.delete(portfolio);
        	transaction.commit();
        }finally{
        	session.close();
        }

	}

	public void executeOrder(String userName, String stockCode, double price, int number){
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			
			User user = userDAO.findUserByName(userName);
			
			if(user == null){
				throw new IllegalArgumentException("User does not exist");
			}
			
			Portfolio portfolio = retrievePortfolio(userName);
			
			if(portfolio == null){
				throw new IllegalArgumentException("User portfolio does not exist");
			}

			//create share if needed
			Share share = (Share) session.get(Share.class, stockCode);
			if(share == null){
				LOG.debug("Creating new share type " + stockCode);
				Transaction xaction = session.beginTransaction();
				Share newShare = new Share();
				newShare.setCode(stockCode);
				session.merge(newShare);
				xaction.commit();
				
				share = newShare;
			}
			transaction = session.beginTransaction();
			
			//Add new stock and number of stocks to the user portfolio
			portfolio.setUserId(user.getId());
			Stock stock = new Stock();
			stock.setNumber(number);
			//stock.setPortfolio(portfolio);
			
			stock.setPortfolioId(portfolio.getPortfolioId());
			stock.setShare(share);
			stock.setPurchasePrice(price);
			
			portfolio.getStocks().add(stock);
			session.merge(stock);
			
			transaction.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		
		
		
//		Session session = sessionFactory.openSession();
//		Transaction transaction = null;
//		
//		try {
//			transaction = session.beginTransaction();
//			session.delete(userDAO.retrieveUserId(username));
//			
//			transaction.commit();
//		} catch (HibernateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			transaction.rollback();
//			
//		} finally{
//			session.close();
//		}
	}


	@Override
	public void placeOrder(TradeOrder job) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.merge(job);
			transaction.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			LOG.error("Writing of portfolio failed! ", e);
			//e.printStackTrace();
			transaction.rollback();
			throw e;
			
		} finally{
			session.close();
		}
		
	}
	
	
	@Override
	public List<TradeOrder> getOrdersInProgress() {
		Session session = sessionFactory.openSession();
		
		try {
			Query query = session.createQuery("from TradeOrder t where t.status != 'COMPLETED' ");
        	return (List<TradeOrder>) query.list();
		
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			LOG.error("Writing of portfolio failed! ", e);
			//e.printStackTrace();
			throw e;
			
		} finally{
			session.close();
		}
		
	}
	
}
