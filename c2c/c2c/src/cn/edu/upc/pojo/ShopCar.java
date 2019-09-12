package cn.edu.upc.pojo;

import java.io.Serializable;

public class ShopCar implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;

    private String modified;

    private Integer display;

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
}