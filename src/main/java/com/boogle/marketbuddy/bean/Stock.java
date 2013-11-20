package com.boogle.marketbuddy.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author bfach
 *
 */
@Table(name="portfolio_stocks")
@Entity
public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9001117207688448789L;

	public Share getShare() {
		return share;
	}

	public void setShare(Share share) {
		this.share = share;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

//	@Id
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "code")
	private Share share;
	
	private double number;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "stock_id")
	private int stockId;
	
	@Column(name = "purchase_price")
	private double purchasePrice;
	
	//@ManyToOne
//	@Id
	@JoinColumn(name="portfolio_id")
	@ManyToOne
	private Portfolio portfolio;
	
//	public double calculateTotal(){
//		return share.getPrice() * number;
//	}

	@ManyToOne(fetch=FetchType.EAGER)
	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	
	public int getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	private int portfolioId;
	
	
}
