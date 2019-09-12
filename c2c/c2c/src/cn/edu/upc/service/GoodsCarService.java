package cn.edu.upc.service;


import java.util.List;

import cn.edu.upc.pojo.GoodsCar;

public interface GoodsCarService {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsCar record);

    int insertSelective(GoodsCar record);

    GoodsCar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsCar record);

    int updateByPrimaryKey(GoodsCar record);


    List<GoodsCar> selectByUid(int uid);
}
