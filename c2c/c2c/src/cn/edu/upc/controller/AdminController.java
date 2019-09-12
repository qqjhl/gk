package cn.edu.upc.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.upc.pojo.AdminInformation;
import cn.edu.upc.pojo.UserInformation;
import cn.edu.upc.response.BaseResponse;
import cn.edu.upc.service.AdminInformationService;
import cn.edu.upc.service.UserInformationService;
import cn.edu.upc.tool.SampleTime;
import cn.edu.upc.tool.StringUtils;

/**
 * 管理员授权，销权
 * @author JingHongLi
 *
 */
@Controller
public class AdminController {
	
	@Resource
	private AdminInformationService adminInformationService;
	@Resource
    private UserInformationService userInformationService;
	
	/**
	 * 授予管理员权限
	 * @param request
	 * @param model
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/accredit.do",  method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse accredit(HttpServletRequest request, Model model,
			@RequestParam String phone) {
		
		System.out.println("AdminController--accredit--accredit.do");
		
		//当前用户信息实体
		UserInformation currentInfo = (UserInformation) request.getSession().getAttribute("userInformation"); 
		//当前用户管理员信息实体
		AdminInformation currentAdmin = adminInformationService.selectByUid(currentInfo.getId());
		
		//当前用户不是管理员,没有授权权限 
		if(StringUtils.getInstance().isNullOrEmpty(currentAdmin)) {
			return BaseResponse.fail("当前用户不是管理员,没有授权权限 ");
		}
		
		int uid =  userInformationService.selectIdByPhone(phone);
		//待授权账号不存在
		if(uid == 0) {
			return BaseResponse.fail("待授权账号不存在");
		}
		
		//带授权账户信息
		AdminInformation waitInfo = adminInformationService.selectByUid(uid);
		
		if(!StringUtils.getInstance().isNullOrEmpty(waitInfo)) {
			//待授权用户已经是管理员
			return BaseResponse.fail("待授权用户已经是管理员");
		}
		
		//带授权用户不是管理员
		
		AdminInformation newAdmin = new AdminInformation();
		newAdmin.setUid(uid);
		newAdmin.setAccredit(currentInfo.getId());
		newAdmin.setModified(SampleTime.getTime(new Date()));
		
		int result = adminInformationService.insert(newAdmin);
		if(result == 1) {
			return BaseResponse.success();
		}
		return BaseResponse.fail();
	}
	
	/**
	 * 撤销管理员权限
	 * @param request
	 * @param model
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/unaccredit.do",  method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse unaccredit(HttpServletRequest request, Model model,
			@RequestParam String phone) {
		
		System.out.println("AdminController--unaccredit--unaccredit.do");
		
		//当前用户信息实体
		UserInformation currentInfo = (UserInformation) request.getSession().getAttribute("userInformation"); 
		//当前用户管理员信息实体
		AdminInformation currentAdmin = adminInformationService.selectByUid(currentInfo.getId());
		
		//当前用户不是管理员,没有授权权限 
		if(StringUtils.getInstance().isNullOrEmpty(currentAdmin)) {
			return BaseResponse.fail("当前用户不是管理员,没有销权权限 ");
		}
		
		int uid =  userInformationService.selectIdByPhone(phone);
		//待销权账号不存在
		if(uid == 0) {
			return BaseResponse.fail("待授权账号不存在");
		}
		
		//带授权账户信息
		AdminInformation waitInfo = adminInformationService.selectByUid(uid);
		
		if(StringUtils.getInstance().isNullOrEmpty(waitInfo)) {
			//待销权用户不是管理员
			return BaseResponse.fail("待销权用户不是管理员");
		}
		
		//待销权用户是管理员
		int result = adminInformationService.deleteByPrimaryKey(waitInfo.getMid());
		if(result == 1) {
			return BaseResponse.success();
		}
		return BaseResponse.fail();
	}
	
}
