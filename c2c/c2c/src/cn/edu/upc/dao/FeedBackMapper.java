package cn.edu.upc.dao;

import java.util.List;

import cn.edu.upc.pojo.FeedBack;

public interface FeedBackMapper {
	List<FeedBack> findAll();
	
	int insert(FeedBack feedBack);
}
