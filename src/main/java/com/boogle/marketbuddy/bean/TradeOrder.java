package com.boogle.marketbuddy.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="trade_orders")
@Entity
public class TradeOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1135902329794925420L;
	
	@Column(name = "stock_code")
	private String stockCode;
	@Column(name = "username")
	private String username;
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	private Double price;
	private Integer number;
	@Id
	private Long ts;
	private String status;
	
	
	public String getUsername() {
		return username;
	}
	
	public String getStockCode() {
		return stockCode;
	}

	public Double getPrice() {
		return price;
	}

	

	public Integer getNumber() {
		return number;
	}

	public Long getTs() {
		return ts;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
