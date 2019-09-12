package cn.edu.upc.bean;

import java.io.Serializable;

/**
 * username   context   modified
 * @author JingHongLi
 *
 */
public class UserContextBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 一条消息的来源
	 */
	private String username;
	private String context;
	private String modified;
	private String sendid;
	
	public String getSendid() {
		return sendid;
	}
	public void setSendid(String sendid) {
		this.sendid = sendid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) { //new Date().toString();
		this.modified = modified;
	}
	
}
