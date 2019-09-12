package cn.edu.upc.service;

import java.util.List;

import cn.edu.upc.pojo.UserAddr;

public interface UserAddrService {

UserAddr findById(int uid);
	
	List<UserAddr> findAll();
	
	int updateAddr(UserAddr userAddr);
	
	int insertAddr(UserAddr userAddr);
	
}
