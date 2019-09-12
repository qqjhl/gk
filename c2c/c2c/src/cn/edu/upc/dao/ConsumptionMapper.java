package cn.edu.upc.dao;

import java.util.List;

import cn.edu.upc.pojo.Consumption;

public interface ConsumptionMapper {
	
	List<Consumption>  findAll();
	
	List<Consumption> findBuyer(int buyer);
	
	List<Consumption> findSeller(int seller);
	
	List<Consumption> findGoods(int sid);
	
	int deleteByPrimaryKey(int id);
	
	int  insert(Consumption consumption);
}
