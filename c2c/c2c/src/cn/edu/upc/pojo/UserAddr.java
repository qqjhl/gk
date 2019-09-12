package cn.edu.upc.pojo;

import java.io.Serializable;

public class UserAddr implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int uid;
	
	private String addr;
	
	private String phone;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
}
