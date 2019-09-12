package cn.edu.upc.dao;

import java.util.List;

import cn.edu.upc.pojo.RepealRecord;

public interface RepealRecordMapper {
	
	List<RepealRecord> selectAll();
	
	List<RepealRecord> selectByAuditing(int auditing);
	
	int insertRecord(RepealRecord repealRecord);
	
	int deleteRecord(int id);
	
}
