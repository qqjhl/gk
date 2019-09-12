package cn.edu.upc.error;

import lombok.Data;

/**
 * 业务异常类
 */
@Data
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int code;
    private String msg;

    public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
