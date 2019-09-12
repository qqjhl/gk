package cn.edu.upc.pojo;

import java.io.Serializable;

/**
 * 购物车
 * @author JingHongLi
 *
 */
public class GoodsCar implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;

    private String modified;
    /**
     * 商品id
     */
    private Integer sid;
    /**
     * 用户id
     */
    private Integer uid;

    private Integer quantity;

    private Integer display;

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

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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