package cn.edu.upc.pojo;

import java.io.Serializable;

public class UserContext implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String modified;
	private int sendid;
	private int acceptid;
	private String context;
	private String stat;
	
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public int getSendid() {
		return sendid;
	}
	public void setSendid(int sendid) {
		this.sendid = sendid;
	}
	public int getAcceptid() {
		return acceptid;
	}
	public void setAcceptid(int acceptid) {
		this.acceptid = acceptid;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
}
