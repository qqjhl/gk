package cn.edu.upc.dao;

import java.util.List;

import cn.edu.upc.pojo.AdminInformation;

/**
 * 
 * @author JingHongLi
 *
 */
public interface AdminInformationMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(AdminInformation record);

    int insertSelective(AdminInformation record);

    AdminInformation selectByUid(Integer uid);

    int updateByPrimaryKeySelective(AdminInformation record);

    int updateByPrimaryKey(AdminInformation record);

    List<AdminInformation> selectByAccredit(int accredit);
}