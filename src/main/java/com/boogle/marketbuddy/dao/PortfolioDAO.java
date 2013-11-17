package com.boogle.marketbuddy.dao;

import com.boogle.marketbuddy.bean.Portfolio;

public interface PortfolioDAO {

	
	void writePortfolio(Portfolio portfolio, String username);
	
	Portfolio retrievePortfolio(String username);
	
	void deletePortfolio(String username);
	
}
