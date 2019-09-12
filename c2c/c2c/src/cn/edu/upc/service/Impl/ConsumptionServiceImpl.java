package cn.edu.upc.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.upc.dao.ConsumptionMapper;
import cn.edu.upc.pojo.Consumption;
import cn.edu.upc.service.ConsumptionService;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {
	@Resource
	private ConsumptionMapper consumptionMapper;
	
	@Override
	public List<Consumption> findBuyer(int buyer) {
		return consumptionMapper.findBuyer(buyer);
	}

	@Override
	public List<Consumption> findSeller(int seller) {
		return consumptionMapper.findSeller(seller);
	}

	@Override
	public List<Consumption> findGoods(int sid) {
		return consumptionMapper.findGoods(sid);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return consumptionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Consumption consumption) {
		return consumptionMapper.insert(consumption);
	}

	@Override
	public List<Consumption> findAll() {
		return consumptionMapper.findAll();
	}

}
