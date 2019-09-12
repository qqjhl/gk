package cn.edu.upc.service.Impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import cn.edu.upc.redis.JedisTool;
import cn.edu.upc.service.RushToBuyService;
import redis.clients.jedis.Jedis;

@Service
public class RushToBuyServiceImpl implements RushToBuyService {
	
	Logger log = Logger.getLogger(RushToBuyServiceImpl.class);
	
	/**
	 * redis连接
	 */
	private Jedis jedis = new Jedis(JedisTool.ip, JedisTool.port);
	
	/**
	 * 返回 -1 	重复抢
	 * 返回  1	成功抢到
	 * 返回  0	商品已抢完啦
	 */
	@Override
	public Long rush(int userId) {
		Long result = (Long) jedis.eval(JedisTool.luaScript, 4, JedisTool.goodsList, 
				JedisTool.goodsConsumedSet, JedisTool.goodsConsumedList, ""+userId);
		return result;
	}

	/**
	 * 先将商品分好，load 内存
	 */
	@Override
	public Long publish(int sid, int count) {
		jedis.flushAll();
		log.debug("RushToBuyServiceImpl--publish--发布抢购商品 sid="+sid+"\t count="+count);
		
		for(int i = 1; i <= count; i++) {
			JSONObject json = new JSONObject();
        	json.put("money", 99);
        	json.put("id", i);
        	jedis.lpush(JedisTool.goodsList, json.toJSONString());
		}
		
		return jedis.llen(JedisTool.goodsList);
	}

}
