package cn.edu.upc.service;

public interface RushToBuyService {
	Long rush(int userId);
	
	Long publish(int sid, int count);
}
