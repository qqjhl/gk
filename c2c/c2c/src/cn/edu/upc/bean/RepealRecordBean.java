package cn.edu.upc.bean;

import java.io.Serializable;

public class RepealRecordBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商品名
	 */
	private String goodsname;
	
	/**
	 * 商品发布人 用户名
	 */
	private String publishuser;
	
	/**
	 * 审核人 用户名
	 */
	private String auditing;
	
	/**
	 * 撤销原因
	 */
	private String reason;
	
	/**
	 * 修改时间
	 */
	private String modified;

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getPublishuser() {
		return publishuser;
	}

	public void setPublishuser(String publishuser) {
		this.publishuser = publishuser;
	}

	public String getAuditing() {
		return auditing;
	}

	public void setAuditing(String auditing) {
		this.auditing = auditing;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

}
