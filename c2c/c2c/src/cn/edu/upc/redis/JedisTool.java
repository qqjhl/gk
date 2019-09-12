package cn.edu.upc.redis;

public class JedisTool {
	
	public static String ip = "192.168.99.100";
	public static int port = 6379;
	/**
	 * 默认参与抢购的商品数量
	 */
	public static int default_count = 100;
	
	/**
	 * 事先生成的商品  sid  list
	 */
	public static String goodsList = "gList";
	
	/**
	 * uid  rid   键值对
	 */
    public static String goodsConsumedList = "gcList";
    
    /**
     * 已经抢到商品的消费者集合
     */
    public static String goodsConsumedSet = "rcSet";
    
    /**
     * lua脚本
     */
    public static String luaScript = 
	   		"if redis.call('sismember', KEYS[2], KEYS[4]) ~= 0 then\n" + 
	   		"    return -1;\n" + 
	   		"else \n" + 
	   		"    local redpacket = redis.call('rpop', KEYS[1]);\n" + 
	   		"    if redpacket then\n" + 
	   		"        local red = cjson.decode(redpacket);\n" + " print(red)" + 
	   		"        red['userId'] = KEYS[4];\n" + 
	   		"        local re = cjson.encode(red);\n" + 
	   		"        redis.call('sadd', KEYS[2], KEYS[4]);\n" + 
	   		"        redis.call('lpush', KEYS[3], re);\n" + 
	   		"        return 1;\n" + 
	   		"    else \n" + 
	   		"        return 0;\n" + 
	   		"    end\n" + 
	   		"end\n";
}
