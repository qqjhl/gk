package cn.edu.upc.service;

import java.util.List;

import cn.edu.upc.pojo.FeedBack;

public interface FeedBackService {
	List<FeedBack> findAll();
	
	int insert(FeedBack feedBack);
}
