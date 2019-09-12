package cn.edu.upc.dao;

import java.util.List;

import cn.edu.upc.pojo.UserAddr;

/**
 * 地址信息
 * @author JingHongLi
 *
 */
public interface UserAddrMapper {
	
	UserAddr findById(int uid);
	
	List<UserAddr> findAll();
	
	int updateAddr(UserAddr userAddr);
	
	int insertAddr(UserAddr userAddr);
}
