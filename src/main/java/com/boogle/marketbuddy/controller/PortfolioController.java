package com.boogle.marketbuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boogle.marketbuddy.bean.Portfolio;
import com.boogle.marketbuddy.bean.Stock;
import com.boogle.marketbuddy.bean.User;
import com.boogle.marketbuddy.dao.PortfolioDAO;
import com.boogle.marketbuddy.dao.UserDAO;

@Controller
public class PortfolioController {

	@Autowired
	private PortfolioDAO portfolioDAO;
	@Autowired
	private UserDAO dao;
	
	
	@RequestMapping(value = "/portfolios/{username}", method = RequestMethod.POST)
	@ResponseBody
	public String addAccount(@PathVariable String username, @RequestBody Portfolio portfolio) {
		portfolioDAO.writePortfolio(portfolio, username);
		
		return "Portfolio for user " + username +" added";
	}
	
	@RequestMapping(value = "/portfolios/{username}", method = RequestMethod.GET)
	@ResponseBody
	public Portfolio getPortfolio(@PathVariable String username) {
		return portfolioDAO.retrievePortfolio(username);
	}

	@RequestMapping(value = "/portfolios/{username}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String username) {
		portfolioDAO.deletePortfolio(username);
	}
	
	@RequestMapping(value = "/portfolios/scheduleTrade/{username}", method = RequestMethod.POST)
	@ResponseBody
	public String makeTrade(@PathVariable String username, String stockCode, Double price, Integer number){
		User toUser = dao.findUserByName(username);
		
		if(toUser == null){
			throw new IllegalArgumentException("Users do not exist");
		}
		
		portfolioDAO.placeOrder(username, stockCode, price, number);
		
		return "TRADE MADE";
		
	}

	private Stock retrieveStock(Stock stock, Portfolio fromPortfolio) {
		//get stock from from user and give to to user
		for(Stock tradeStock : fromPortfolio.getStocks()){
			if(tradeStock.getShare().getCode().equalsIgnoreCase(stock.getShare().getCode())){
				return tradeStock;
			}
		}
		return null;
	}

}
