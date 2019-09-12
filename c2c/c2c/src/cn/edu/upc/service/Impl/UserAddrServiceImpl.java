package cn.edu.upc.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.upc.dao.UserAddrMapper;
import cn.edu.upc.pojo.UserAddr;
import cn.edu.upc.service.UserAddrService;

@Service
public class UserAddrServiceImpl implements UserAddrService {
	@Resource
	private UserAddrMapper userAddrMapper;

	@Override
	public UserAddr findById(int uid) {
		return userAddrMapper.findById(uid);
	}

	@Override
	public List<UserAddr> findAll() {
		return userAddrMapper.findAll();
	}

	@Override
	public int updateAddr(UserAddr userAddr) {
		return userAddrMapper.updateAddr(userAddr);
	}

	@Override
	public int insertAddr(UserAddr userAddr) {
		return userAddrMapper.insertAddr(userAddr);
	}
}
