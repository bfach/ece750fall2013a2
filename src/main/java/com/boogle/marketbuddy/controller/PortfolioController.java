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
	
	@RequestMapping(value = "/portfolios/makeTrade/{fromUsername}/{toUsername}", method = RequestMethod.POST)
	@ResponseBody
	public String makeTrade(@PathVariable String fromUsername, @PathVariable String toUsername, Stock stock){
		User fromUser = dao.findUserByName(fromUsername);
		User toUser = dao.findUserByName(toUsername);
		
		if(fromUser == null | toUser == null){
			throw new IllegalArgumentException("Users do not exist");
		}
		
		Portfolio fromPortfolio = this.getPortfolio(fromUser.getUsername());
		Portfolio toPortfolio = this.getPortfolio(toUser.getUsername());
		
		Stock saleStock = retrieveStock(stock, fromPortfolio);
		
		
		fromPortfolio.getStocks().remove(saleStock);
		toPortfolio.getStocks().add(saleStock);

		//portfolioDAO.writePortfolio(fromPortfolio, toPortfolio);
		
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
