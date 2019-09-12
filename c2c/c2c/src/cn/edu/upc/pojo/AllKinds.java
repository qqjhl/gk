package cn.edu.upc.pojo;

import java.io.Serializable;

public class AllKinds implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;

    private String name;

    private String modified;

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
        this.name = name == null ? null : name.trim();
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}