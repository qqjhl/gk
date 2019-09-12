package cn.edu.upc.service;

import java.util.List;

import cn.edu.upc.pojo.UserContext;

public interface UserContextService {
	UserContext selectByPrimaryKey(int id);
	
	List<UserContext> sender(int sendid);
	
	List<UserContext> accepter(int acceptid);
	
	int deleteByPrimaryKey(int id);
	
	int insert(UserContext userContext);
	
	int updateStat(UserContext userContext);
}
