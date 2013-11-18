package com.boogle.marketbuddy.controller;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boogle.marketbuddy.bean.Portfolio;
import com.boogle.marketbuddy.bean.Stock;
import com.boogle.marketbuddy.bean.Trade;
import com.boogle.marketbuddy.bean.TradeOrder;
import com.boogle.marketbuddy.bean.User;
import com.boogle.marketbuddy.dao.PortfolioDAO;
import com.boogle.marketbuddy.dao.UserDAO;

@Controller
public class PortfolioController {

	@Autowired
	private PortfolioDAO portfolioDAO;
	@Autowired
	private UserDAO dao;
	
	private final BlockingQueue<TradeOrder> workQueue;
    private final ExecutorService service;

    int numWorkers = 1;
    private static final int QUEUE_SIZE = 10000;
	
    public PortfolioController() {
        workQueue = new LinkedBlockingQueue<TradeOrder>(QUEUE_SIZE);
        service = Executors.newFixedThreadPool(numWorkers);

        
        for (int i=0; i < numWorkers; i++) {
            service.submit(new Worker<TradeOrder>(workQueue));
        }
    }
    
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
	public String makeTrade(@PathVariable String username,  @RequestBody Trade trade){
		User toUser = dao.findUserByName(username);
		
		if(toUser == null){
			throw new IllegalArgumentException("Users do not exist");
		}
		
		
		//add to a queue and update the db to indicate the new job
		TradeOrder order = new TradeOrder();
		order.setNumber(trade.getNumber());
		order.setPrice(trade.getPrice());
		order.setStatus("SCHEDULED");
		order.setStockCode(trade.getStockCode());
		order.setTs(System.currentTimeMillis());
		order.setUsername(toUser.getUsername());
		
		workQueue.add(order);
		portfolioDAO.placeOrder(order);
		return "TRADE MADE";
		
	}

	@RequestMapping(value = "/portfolios/scheduledTrades", method = RequestMethod.GET)
	@ResponseBody
	public List<TradeOrder> getOrders(){
		return portfolioDAO.getOrdersInProgress();
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

	
	private class Worker<T> implements Runnable {
        private final BlockingQueue<T> workQueue;

        public Worker(BlockingQueue<T> workQueue) {
            this.workQueue = workQueue;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                	TradeOrder tradeJob = (TradeOrder) workQueue.take();
                    tradeJob.setStatus("IN PROGESS");
                    
                    Thread.sleep(20000);
                    
                    portfolioDAO.executeOrder(tradeJob.getUsername(), tradeJob.getStockCode(), tradeJob.getPrice(), tradeJob.getNumber());
            		
                    tradeJob.setStatus("COMPLETED");
                    portfolioDAO.placeOrder(tradeJob);
                    
                    // Process item
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
