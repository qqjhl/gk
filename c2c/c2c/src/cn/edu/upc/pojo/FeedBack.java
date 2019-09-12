package cn.edu.upc.pojo;

import java.io.Serializable;

/**
 * 反馈
 * @author JingHongLi
 *
 */
public class FeedBack implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int uid;
	private String context;
	private String phone;
	private String email;
	private String modified;
	private int display;
	
	public int getDisplay() {
		return display;
	}
	public void setDisplay(int display) {
		this.display = display;
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
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
}
