package cn.edu.upc.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.upc.dao.RepealRecordMapper;
import cn.edu.upc.pojo.RepealRecord;
import cn.edu.upc.service.RepealRecordService;

@Service
public class RepealRecordServiceImpl implements RepealRecordService {
	@Resource
	private RepealRecordMapper repealRecordMapper;

	@Override
	public List<RepealRecord> selectAll() {
		return repealRecordMapper.selectAll();
	}

	@Override
	public List<RepealRecord> selectByAuditing(int auditing) {
		return repealRecordMapper.selectByAuditing(auditing);
	}

	@Override
	public int insertRecord(RepealRecord repealRecord) {
		return repealRecordMapper.insertRecord(repealRecord);
	}

	@Override
	public int deleteRecord(int id) {
		return repealRecordMapper.deleteRecord(id);
	}
	
	
	
}
