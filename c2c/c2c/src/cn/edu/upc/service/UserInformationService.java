package cn.edu.upc.service;


import java.util.List;

import cn.edu.upc.pojo.UserInformation;
import cn.edu.upc.util.DataSource;

/**
 * 用户信息业务处理
 */
public interface UserInformationService {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInformation record);

    int insertSelective(UserInformation record);

    @DataSource("slave")
    UserInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInformation record);

    int updateByPrimaryKey(UserInformation record);
    /**
     * 通过phone查询id
     * @param phone
     * @return
     */
    @DataSource("slave")
    int selectIdByPhone(String phone);

    List<UserInformation> getAllForeach(List<Integer> list);
}
