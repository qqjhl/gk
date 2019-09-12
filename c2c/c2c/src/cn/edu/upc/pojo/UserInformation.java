package cn.edu.upc.pojo;

import java.io.Serializable;

/**
 * 用户信息
 * @author JingHongLi
 *
 */
public class UserInformation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
    private Integer id;
    
    /**
     * 修改时间
     */
    private String modified;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 用户电话
     */
    private String phone;
    
    /**
     * 真实姓名
     */
    private String realname;
    
    /**
     * 班级
     */
    private String clazz;
    
    /**
     * 学号
     */
    private String sno;
    
    /**
     * 宿舍
     */
    private String dormitory;
    
    /**
     * 性别
     */
    private String gender;
    
    /**
     * 创建时间
     */
    private String createtime;
    
    /**
     * 行为标识
     */
    private String avatar;
    
    /**
     * 邮箱
     */
    private String email;

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getRealname() {
        return realname;
    }
    
    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno == null ? null : sno.trim();
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory == null ? null : dormitory.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}