package cn.edu.upc.pojo;

import java.io.Serializable;

/**
 * 用户密码
 * @author JingHongLi
 *
 */
public class UserPassword implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;
    
    /**
     * 修改时间
     */
    private String modified;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 用户id
     */
    private Integer uid;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}