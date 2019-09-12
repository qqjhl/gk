package cn.edu.upc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.upc.pojo.UserInformation;
import cn.edu.upc.pojo.UserPassword;
import cn.edu.upc.response.BaseResponse;
import cn.edu.upc.service.UserInformationService;
import cn.edu.upc.service.UserPasswordService;
import cn.edu.upc.tool.SampleTime;
import cn.edu.upc.tool.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * 注册中心
 */
@Controller
public class RegisterController {
    @Resource
    private UserPasswordService userPasswordService;

    @Resource
    private UserInformationService userInformationService;

    /**
     * 开始注册用户
     * @param request
     * @param password
     * @param token
     * @return
     */
    @RequestMapping("/insertUser.do")
    @ResponseBody
    public BaseResponse insertUser(HttpServletRequest request,
                                   @RequestParam String password,@RequestParam String phone, @RequestParam String token) {
    	
    	System.out.println("RegisterController--insertUser--insertUser.do");
    	
    	//不是手机号，注册失败
    	if(!StringUtils.getInstance().isPhone(phone)) {
    		return BaseResponse.fail("手机号格式不对");
    	}
    	
        //存储与session中的手机号码
    	request.getSession().setAttribute("phone", phone);
        
        //token，唯一标识
        String insertUserToken = (String) request.getSession().getAttribute("token");
        
        //防止重复提交
        if (StringUtils.getInstance().isNullOrEmpty(insertUserToken) || !insertUserToken.equals(token)) {
            return BaseResponse.fail();
        }
        //该手机号码已经存在
        int uid = userInformationService.selectIdByPhone(phone);
        if (uid != 0) {
            return BaseResponse.fail("该手机号已存在");
        }
        

        //用户信息
        UserInformation userInformation = new UserInformation();
        userInformation.setPhone(phone);
        userInformation.setCreatetime(SampleTime.getTime(new Date()));
        String username = (String) request.getSession().getAttribute("name");
        userInformation.setUsername(phone);
        userInformation.setModified(SampleTime.getTime(new Date()));
//        //测试万能用户
//        UserInformation userInformation = new UserInformation();
//        userInformation.setPhone("0000");
//        userInformation.setUsername("lsj");
//        userInformation.setModified(SampleTime.getTime(new Date()));
        
        
        int result;
        result = userInformationService.insertSelective(userInformation);
        
        //如果用户基本信息写入成功
        if (result == 1) {
            uid = userInformationService.selectIdByPhone(phone);
            String newPassword = StringUtils.getInstance().getMD5(password);
            UserPassword userPassword = new UserPassword();
            userPassword.setModified(SampleTime.getTime(new Date()));
            userPassword.setUid(uid);
            userPassword.setPassword(newPassword);
            result = userPasswordService.insertSelective(userPassword);
            
            //密码写入失败
            if (result != 1) {
                userInformationService.deleteByPrimaryKey(uid);
                return BaseResponse.fail("密码写入失败");
            } else {
                //注册成功
                userInformation = userInformationService.selectByPrimaryKey(uid);
                request.getSession().setAttribute("userInformation", userInformation);
                return BaseResponse.success();
            }
        }
        return BaseResponse.fail("你猜猜是什么错误");
    }
    
    @RequestMapping("/checkCode.do")
    @ResponseBody
    public BaseResponse checkCode(HttpServletRequest request, @RequestParam String name, 
    		@RequestParam String phone, @RequestParam String code, @RequestParam String token) {
    	
    	System.out.println("RegisterController--checkCode--checkCode.do");
    	
    	request.getSession().setAttribute("phone", phone);
    	return BaseResponse.success();
    }
    
    @RequestMapping("/sendCode.do")
    @ResponseBody
    public BaseResponse sendCode(HttpServletRequest request, @RequestParam String phone, 
    		@RequestParam String token, @RequestParam String action) {
    	
    	System.out.println("RegisterController--sendCode--sendCode.do");
    	if(action.equals("register")) {  //发送注册验证码
    		
    		return BaseResponse.success();
    	}
    	request.getSession().setAttribute("phone", phone);
    	return BaseResponse.success();
    }
}
