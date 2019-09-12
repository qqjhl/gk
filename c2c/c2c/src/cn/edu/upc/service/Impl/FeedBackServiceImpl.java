package cn.edu.upc.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.upc.dao.FeedBackMapper;
import cn.edu.upc.pojo.FeedBack;
import cn.edu.upc.service.FeedBackService;
@Service
public class FeedBackServiceImpl implements FeedBackService {
	@Resource
	private FeedBackMapper feedBackMapper;
	
	@Override
	public List<FeedBack> findAll() {
		// TODO Auto-generated method stub
		return feedBackMapper.findAll();
	}

	@Override
	public int insert(FeedBack feedBack) {
		return feedBackMapper.insert(feedBack);
	}

}
