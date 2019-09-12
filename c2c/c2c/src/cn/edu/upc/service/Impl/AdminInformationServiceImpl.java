package cn.edu.upc.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.upc.dao.AdminInformationMapper;
import cn.edu.upc.pojo.AdminInformation;
import cn.edu.upc.service.AdminInformationService;

@Service
public class AdminInformationServiceImpl implements AdminInformationService {
	
	@Resource
	private AdminInformationMapper adminInformationMapper;
	
	/**
	 * 通过uid查询管理员信息
	 */
	@Override
	public AdminInformation selectByUid(int uid) {
		return adminInformationMapper.selectByUid(uid);
	}
	
	/**
	 * 查询授权人所以授权记录
	 */
	@Override
	public List<AdminInformation> selectByAccredit(int accredit) {
		
		return adminInformationMapper.selectByAccredit(accredit);
	}

	@Override
	public int deleteByPrimaryKey(int mid) {
		return adminInformationMapper.deleteByPrimaryKey(mid);
	}

	@Override
	public int insert(AdminInformation adminInformation) {
		return adminInformationMapper.insert(adminInformation);
	}

	@Override
	public int insertSelective(AdminInformation adminInformation) {
		return adminInformationMapper.insertSelective(adminInformation);
	}

	@Override
	public int updateByPrimaryKeySelective(AdminInformation adminInformation) {
		return adminInformationMapper.updateByPrimaryKeySelective(adminInformation);
	}

	@Override
	public int updateByPrimaryKey(AdminInformation adminInformation) {
		return adminInformationMapper.updateByPrimaryKey(adminInformation);
	}

}
