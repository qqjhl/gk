package cn.edu.upc.bean;

import java.io.Serializable;

public class ConsumptionBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String goodsname;
	
	private String io;
	
	/**
	 * 交易对象
	 */
	private String transactors;
	
	private int counts;
	
	private int money;
	
	private String modified;
	
	
	
	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getIo() {
		return io;
	}

	public void setIo(String io) {
		this.io = io;
	}

	public String getTransactors() {
		return transactors;
	}

	public void setTransactors(String transactors) {
		this.transactors = transactors;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	
	
}
