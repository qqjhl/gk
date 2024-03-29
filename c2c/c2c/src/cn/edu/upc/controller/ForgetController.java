package cn.edu.upc.controller;

import cn.edu.upc.pojo.UserInformation;
import cn.edu.upc.pojo.UserPassword;
import cn.edu.upc.service.UserInformationService;
import cn.edu.upc.service.UserPasswordService;
import cn.edu.upc.tool.SampleTime;
import cn.edu.upc.tool.StringUtils;

//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 忘记密码
 */
@RestController
public class ForgetController {

    @Resource
    private UserPasswordService userPasswordService;
    @Resource
    private UserInformationService userInformationService;

    /**
     * 注册页面，下一步按钮调用，检查输入信息
     * @param request
     * @param model
     * @param code
     * @param token
     * @return
     */
    @RequestMapping(value = "checkCode.do", method = {RequestMethod.POST, RequestMethod.GET})
    public Map<String, Integer> checkPhone(HttpServletRequest request, Model model,
                          @RequestParam String code, @RequestParam String token) {
        //get the phone which is saved in the session
        Map<String, Integer> map = new HashMap<>();
        String name = request.getParameter("name");
        if (!StringUtils.getInstance().isNullOrEmpty(name)) {
            request.getSession().setAttribute("name", name);
        }
        //judge the token 防止重复提交
        String checkCodeToken = (String) request.getSession().getAttribute("token");
        if (StringUtils.getInstance().isNullOrEmpty(checkCodeToken) || !checkCodeToken.equals(token)) {
            map.put("result", 0);
            return map;
        }
        //验证码错误处理, (默认验证码123456, 默认为 true)
        if (!checkCodePhone(code, request)) {
            map.put("result", 0);
            return map;
        }
        map.put("result", 1);
        return map;
    }

    //更新密码
    @RequestMapping("updatePassword.do")
    public Map<String, Integer> updatePassword(HttpServletRequest request, Model model,
                              @RequestParam String password, @RequestParam String token) {
        //防止重复提交
        String updatePasswordToken = (String) request.getSession().getAttribute("token");
        Map<String, Integer> map = new HashMap<>();
        if (StringUtils.getInstance().isNullOrEmpty(updatePasswordToken) || !updatePasswordToken.equals(token)) {
            map.put("result", 0);
            return map;
        }
        String realPhone = (String) request.getSession().getAttribute("phone");
        UserPassword userPassword = new UserPassword();
        String newPassword = StringUtils.getInstance().getMD5(password);
        int uid;
        try {
            uid = userInformationService.selectIdByPhone(realPhone);
            if (uid == 0) {
                map.put("result", 0);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0);
            return map;
        }
        int id = userPasswordService.selectByUid(uid).getId();
        userPassword.setId(id);
        userPassword.setUid(uid);
        userPassword.setModified(SampleTime.getTime(new Date()));
        userPassword.setPassword(newPassword);
        int result;
        try {
            result = userPasswordService.updateByPrimaryKeySelective(userPassword);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0);
            return map;
        }
        //更新失败
        if (result != 1) {
            map.put("result", 0);
            return map;
        }
        UserInformation userInformation = userInformationService.selectByPrimaryKey(uid);
        request.getSession().setAttribute("userInformation", userInformation);
        map.put("result", 1);
        return map;
    }

    /**
     * 检查手机验证码, 默认为true
     * @param code_phone
     * @param request
     * @return
     */
    private boolean checkCodePhone(String code_phone, HttpServletRequest request) {
        String true_code_phone = "123456";
        return code_phone.equals(true_code_phone);
    }
}
