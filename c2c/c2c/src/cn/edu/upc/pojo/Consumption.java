package cn.edu.upc.pojo;

import java.io.Serializable;

public class Consumption implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private int id;
	
	/**
	 * 商品id
	 */
	private int sid;
	
	/**
	 * 买家
	 */
	private int buyer;
	
	/**
	 * 卖家
	 */
	private int seller;
	
	/**
	 * 交易数目
	 */
	private int counts;
	
	/**
	 * 交易时间
	 */
	private String modified;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getBuyer() {
		return buyer;
	}

	public void setBuyer(int buyer) {
		this.buyer = buyer;
	}

	public int getSeller() {
		return seller;
	}

	public void setSeller(int seller) {
		this.seller = seller;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}
	
}
