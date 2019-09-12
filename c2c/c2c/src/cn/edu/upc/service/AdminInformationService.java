package cn.edu.upc.service;

import java.util.List;

import cn.edu.upc.pojo.AdminInformation;

/**
 * 管理员业务逻辑接口
 * @author JingHongLi
 *
 */
public interface AdminInformationService {
	
	AdminInformation selectByUid(int uid);
	
	List<AdminInformation> selectByAccredit(int accredit);
	
	int deleteByPrimaryKey(int mid); 
	
	int insert(AdminInformation adminInformation);
	
	int insertSelective(AdminInformation adminInformation);
	
	int updateByPrimaryKeySelective(AdminInformation adminInformation);

	int updateByPrimaryKey(AdminInformation adminInformation);
}
