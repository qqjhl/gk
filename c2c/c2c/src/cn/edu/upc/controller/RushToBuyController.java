package cn.edu.upc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.upc.pojo.UserInformation;
import cn.edu.upc.response.BaseResponse;
import cn.edu.upc.service.RushToBuyService;
import cn.edu.upc.tool.StringUtils;

@Controller
public class RushToBuyController {
	
	@Autowired
	private RushToBuyService rushToBuyService;
	
	@RequestMapping(value="/active.do")
	public String active(HttpServletRequest request, Model model) {
		System.out.println("RushToBuyController--active--active.do");
		
		UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");		
		if(StringUtils.getInstance().isNullOrEmpty(userInformation)) {
			return "redirect:/login.do";
		}
		
		model.addAttribute("userInformation", userInformation);
		
		return "page/activities";
	}
	
	/**
	 * 用户抢商品
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/rush.do", method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse rush(HttpServletRequest request, @RequestParam int id) {
		System.out.println("RushToBuyController--rush--rush.do");
		
		Long result = rushToBuyService.rush(id);
		if(result == 1) {
			return BaseResponse.success("success");
		} else if(result == 0) {
			return BaseResponse.fail("商品已被抢光啦 ~ ~ ~");
		} else {
			return BaseResponse.fail("您已抢到商品啦");
		}
		
	}
	
	@RequestMapping(value="/publishRush.do", method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse publish(HttpServletRequest request, @RequestParam int count) {
		System.out.println("RushToBuyController--publish--publishRush.do");
		
		Long result  = rushToBuyService.publish(1, count);
		
		if(result == count) {
			return BaseResponse.success(1, "活动发布成功！！！");
		}
		return BaseResponse.fail();
		
	}
	
}
