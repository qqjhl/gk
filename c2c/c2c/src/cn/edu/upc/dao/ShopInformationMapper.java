package cn.edu.upc.dao;

import cn.edu.upc.pojo.ShopInformation;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ShopInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopInformation record);

    int insertSelective(ShopInformation record);

    ShopInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopInformation record);

    int updateByPrimaryKey(ShopInformation record);

    List<ShopInformation> selectTen(Map<?, ?> map);

    List<ShopInformation> selectOffShelf(Integer uid, Integer start);

    int getCountsOffShelf(Integer uid);

    int getCounts();

    int selectIdByImage(String image);
    
    /**
     * 选择用户
     * @param name
     * @return
     */
    List<ShopInformation> selectByName(String name);

    /**
     * 通过分类选择
     * @param sort
     * @return
     */
    @Select("select * from shopinformation where sort=#{sort} and display =1 limit 12")
    List<ShopInformation> selectBySort(int sort);

    /**
     * 选择用户的发布
     * @param uid
     * @return
     */
    @Select("select * from shopinformation where uid=#{uid} and display=1 order by id desc limit 12")
    List<ShopInformation> selectUserReleaseByUid(int uid);
}