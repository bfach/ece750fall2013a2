package com.boogle.marketbuddy.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name="shares")
@Entity
public class Share implements Serializable {

//	@OneToOne(mappedBy="share")
//	@JoinColumn(name="stock_id")
//	private Stock stock;
	/**
	 * 
	 */
	private static final long serialVersionUID = 9218108863656871795L;
	@Override
	public String toString() {
		return "Share [code=" + code + "]";
	}
	//BBRY for example
	@Id
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
//	public Stock getStock() {
//		return stock;
//	}
//	public void setStock(Stock stock) {
//		this.stock = stock;
//	}
		
}
