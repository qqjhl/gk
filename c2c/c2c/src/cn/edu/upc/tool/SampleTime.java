package cn.edu.upc.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleTime {
	
	public static String getTime(Date modified) {
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(modified);
	}
	
}
