package cn.edu.upc.pojo;

import java.io.Serializable;

/**
 * 发布商品
 * @author JingHongLi
 *
 */
public class UserRelease implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;

    private String modified;

    private Integer display;

    private Integer uid;

    private Integer sid;

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

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }
}