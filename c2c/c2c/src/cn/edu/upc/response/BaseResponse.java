package cn.edu.upc.response;

import cn.edu.upc.handle.GlobalExceptionHandler;
//import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ajax 请求返回对象
 */
@Data
public class BaseResponse {
	
	/**
	 * 0 失败 fail
	 * 1 成功 success
	 */
    private int result;
    
    /**
     * 传递信息
     */
    private String msg;

    public BaseResponse(int result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public BaseResponse(int result) {
        this.result = result;
    }
    
    /**
     * @return <0,DEFAULT_ERROR_MESSAGE>
     */
    public static BaseResponse fail() {
        return new BaseResponse(0, GlobalExceptionHandler.DEFAULT_ERROR_MESSAGE);
    }
    
    /**
     * 
     * @param msg
     * @return  <0,msg>
     */
    public static BaseResponse fail(String msg) {
        return new BaseResponse(0, msg);
    }
    
    /**
     * 
     * @param result
     * @return  <result,"">
     */
    public static BaseResponse fail(int result) {
        return new BaseResponse(result);
    }
    
    /**
     * 
     * @return  <1,success>
     */
    public static BaseResponse success() {
        return new BaseResponse(1, "success");
    }
    
    /**
     * 
     * @param msg
     * @return  <1,msg>
     */
    public static BaseResponse success(String msg) {
        return new BaseResponse(1, msg);
    }
    
    public static BaseResponse success(int result, String msg) {
        return new BaseResponse(result, msg);
    }
    
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    
}
