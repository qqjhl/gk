package cn.edu.upc.pojo;

import java.io.Serializable;

/**
 * 消息审核, 撤销对象
 * @author JingHongLi
 *
 */
public class RepealRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private int id;
	
	/**
	 * 商品名
	 */
	private String goodsname;
	
	/**
	 * 商品发布人 uid
	 */
	private int publishuser;
	
	/**
	 * 审核人 uid
	 */
	private int auditing;
	
	/**
	 * 撤销原因
	 */
	private String reason;
	
	/**
	 * 修改时间
	 */
	private String modified;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public int getPublishuser() {
		return publishuser;
	}

	public void setPublishuser(int publishuser) {
		this.publishuser = publishuser;
	}

	public int getAuditing() {
		return auditing;
	}

	public void setAuditing(int auditing) {
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
