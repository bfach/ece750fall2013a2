package com.boogle.marketbuddy.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="user_portfolio")
@Entity
public class Portfolio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 872809447156458553L;

//	@OneToMany(mappedBy="portfolio", cascade=CascadeType.ALL)
//	
//	@OneToMany(mappedBy="portfolio", cascade=CascadeType.ALL)
//	@JoinTable(name="portfolio_stocks",
//	joinColumns={@JoinColumn(name="portfolio_id")},
//	inverseJoinColumns={@JoinColumn(name="portfolio_id")})
	@OneToMany(mappedBy="portfolio", cascade=CascadeType.ALL)
	private List<Stock> stocks;
	
	@Id
	@Column(name = "user_id")
	private int userId;
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "portfolio_id")
	private int portfolioId;
	
	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public double calculatePortfolioTotal(){
		
		double total = 0;
		
		for(Stock stock : stocks){
			//search for the ticker and get price
			double price = 1;
			stock.getShare().getCode();
			
			total += stock.getNumber();
		}
		
		return total;
	}

	public int getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
	}
}
