package cn.edu.upc.tool;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayUtils {


	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016100100639289";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiA"
			+ "gEAAoIBAQDMJtmNPO+dJmDMH71RcI0/ZYCw6iLfYHTcoEWcN+avnu08WBu+cW1Fuj4e+/udejI4"
			+ "8hQkYQhrHHqVMpj1oH9aRC2GkMXKfj/zHvKzQoHcobjdgtHzEzhtEw6fd2SgJx2CjLT4bTW9esYT"
			+ "7mDHfZVCEbhmjY6QFQ3Pwat3LtYMmbEIOEjMzw3BgmdaudTAsxADm7xro46OUuRtgMXdNLerXE9W"
			+ "+ASGYPbO91MhRJwVwDcVcnvjUYYA6aDcRfhkrI12e7bmZupNcRxiqE0FCCe1n9MvEm5O96CE+7Qq"
			+ "LL5YDUPO8r+0+GmAxNETiavPKHz4YqNX0DZ0N0bLQYXbgaOjAgMBAAECggEARit8T12Ihp1wW/j0"
			+ "248yHX5H84cmoVzIDiVQh9d3BRo9NKacMqNOCc9wFrpJy+D6bjRW1AI+IILTZ4DSrmqSG3afp0cP"
			+ "lQA7/ta+WPsdI7a6kfirLT+H/4WzWwBwp9OKAxtWAKWY+R3AA43F2gvCR8lLdoRlYgS6kJRi2T5R"
			+ "+FYl2DZEb+gALaO3znZ70IisdCflreEER46BLRm46qKNh5dYNuPmHIxZrkxdkzJDFGk/vzvImIDn"
			+ "cM7ForPmrYOf7Mj2wtKRWcUL4gGaLMI7hf/UUhs87p/fFGrRURn9OLsuPLnutNmOpnFZT+4M7xyu"
			+ "5Mvxj0XJv8GVnOcmmDSzIQKBgQDzmfWNrDIh4T785gfEwv9zkPC5hguOWvSk5Jwug8cJji0EBJFH"
			+ "GK/a8K0P50D5SCMSt842OmwuFVHvYdUiGkJ/XoF5hUqSTTRIEuYrej0Aquh1vHWb/2k4XUXCuKls"
			+ "mUcSosFFpd7XLzl/ol5BPR296RZ/RM0kwLS1XLy6+TTnGwKBgQDWiuAli0D6/r6XmBrAG9XI5aD3"
			+ "gxO8u82dBlkxOPofX1b7VbfwuTF7NdfA/iWol2xoncoBMiQZgkkLfgO4hZ7iYW2v3oQON8LBF+VE"
			+ "uRan7+FdeJ493gl9RHETj51Lk8+ukjkeyr/6dtH8fPksu8zPFv4+W7UqDKlxPdLrNlxWGQKBgFA7"
			+ "SA3mRgi0mDtRRAPBOcOFXFOuW4YCXQsmJG6JhneWNStamcKx1dGlbUqMnvC7mpmioi9oJJM1AGx4"
			+ "a2s0/OvYY4pzpTD0bqDCYL63HvpmT/PfI1lC0RP4XG6axWp4PVlc840N6Yw0CJsKDkMsbuxKpdc7A"
			+ "Nn3Ak2tgpeOVqc7AoGAds1yb/fb5OGaIxIchhMj4FqgN0+DZ3jL1E9GpsY5Y5QE86q07/1tVCAtuH"
			+ "ZD3kbzxj7SNLVSXjnSPyXp441CvXNcLQ5YLBNeqlSUJekkjZx9PKhlYDcLJC35O+7EGKziTXY1YUK"
			+ "+6nBPubRuuwHH2UmPQnLAMGfQkJ++o3d3zEkCgYBsdQ8korF7esZ2MU8XKwLwC/RkqtdNYXAoyqg8"
			+ "p4Kqq5oQni3sxKN6b91hRnyZS+emqXhATlLQRcTy8rMnyiX7puPW+TK9Ek/AGHtMG2L7y6C8Loctm"
			+ "ouXTCtoHIgPUyrf+RN4OF+8Ai6HZya8fCfu9++Csi5KCkDXqjVWbYVnsw==";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA"
			+ "pWVCXvikayaiQr30ljFqr+kLY0JT0U8znrpcxRvMyA6BlVDlxHlKBTK8QTUGO0etjdzfdsp/lNB"
			+ "60eLHF8oAfTeQrwCDgrnsysuutWhjIoijXNHAzgDTZFvp6mCr3nk9yzqujQZB73DOpRNZKjLFocW"
			+ "JN5DYYQF/Vl7PvXAjrsRkvRGCC43WCcDGPqDmB7J8GXo/WCZJznO8y7TRGQSEZAcpmNNdQS4bG6y"
			+ "ll2Ditjn0kdWUW+xzxP7ymVIulEh9e0X0jmErdOUzvH2w7pHejssJAe+dcwxTe06TD0j3r+D+m+h"
			+ "woo7Ozo1XcRJLBdhf98ESOr2PG1ne19FUFG2gFQIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "http://localhost:8080/c2c/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/c2c/returnUrl.do";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "C:\\";

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord
	 *            要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
