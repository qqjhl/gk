package cn.edu.upc.pojo;

import java.io.Serializable;

public class Specific implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;

    private String name;

    private String modified;

    private Integer cid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}