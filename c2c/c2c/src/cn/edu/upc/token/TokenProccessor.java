package cn.edu.upc.token;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Token 生成
 * @author JingHongLi
 *
 */
public class TokenProccessor {

    /**
	*	单例设计模式（保证类的对象在内存中只有一个）<br>
	*1、把类的构造函数私有 <br>
	*2、自己创建一个类的对象 <br>
	*3、对外提供一个公共的方法，返回类的对象
	*/
    private TokenProccessor() {
    }

    private static final TokenProccessor instance = new TokenProccessor();

    /**
     * 返回类的对象
     * @return
     */
    public static TokenProccessor getInstance() {
        return instance;
    }

    /**
     * 生成Token  <br>
     * Token：Nv6RRuGEVvmGjB+jimI/gw==
     *
     * @return
     */
    public String makeToken() {  

        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";

        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(token.getBytes());
            //base64编码--任意二进制编码明文字符   
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}