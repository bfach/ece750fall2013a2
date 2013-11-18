package com.boogle.marketbuddy.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="trades")
public class Trade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1135902329794925420L;
	private String stockCode;
	private Double price;
	private Integer number;

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
