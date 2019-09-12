package cn.edu.upc.service;


import java.util.List;

import cn.edu.upc.pojo.ShopContext;

public interface ShopContextService {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopContext record);

    int insertSelective(ShopContext record);

    ShopContext selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopContext record);

    int updateByPrimaryKey(ShopContext record);

    int getCounts(int sid);

    List<ShopContext> findById(int sid, int start);

    List<ShopContext> selectById(int id);
}
