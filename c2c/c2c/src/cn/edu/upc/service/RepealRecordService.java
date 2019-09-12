package cn.edu.upc.service;

import java.util.List;

import cn.edu.upc.pojo.RepealRecord;

public interface RepealRecordService {
	
	List<RepealRecord> selectAll();
	
	List<RepealRecord> selectByAuditing(int auditing);
	
	int insertRecord(RepealRecord repealRecord);
	
	int deleteRecord(int id);
	
}
