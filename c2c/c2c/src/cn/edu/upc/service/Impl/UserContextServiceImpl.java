package cn.edu.upc.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.upc.dao.UserContextMapper;
import cn.edu.upc.pojo.UserContext;
import cn.edu.upc.service.UserContextService;

@Service
public class UserContextServiceImpl implements UserContextService {
	@Resource
	private UserContextMapper userContextMapper;
	
	@Override
	public UserContext selectByPrimaryKey(int id) {
		return userContextMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UserContext> sender(int sendid) {
		return userContextMapper.sender(sendid);
	}

	@Override
	public List<UserContext> accepter(int acceptid) {
		return userContextMapper.accepter(acceptid);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return userContextMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserContext userContext) {
		return userContextMapper.insert(userContext);
	}
	
	public int updateStat(UserContext userContext) {
		return userContextMapper.updateStat(userContext);
	}
	
}
