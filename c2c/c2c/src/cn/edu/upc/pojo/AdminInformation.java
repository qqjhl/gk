package cn.edu.upc.pojo;

import java.io.Serializable;

/**
 * 管理员实体类
 * @author JingHongLi
 *
 */
public class AdminInformation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 管理员id
	 */
	private Integer mid;

	/**
	 * 用户id
	 */
    private Integer uid;
    
    /**
     * 授权人
     */
    private Integer accredit;
    
    /**
     * 修改时间
     */
    private String modified;

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getAccredit() {
		return accredit;
	}

	public void setAccredit(Integer accredit) {
		this.accredit = accredit;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	
}