package cn.edu.upc.controller;

import lombok.extern.slf4j.Slf4j;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.upc.bean.ConsumptionBean;
import cn.edu.upc.bean.FeedBackBean;
import cn.edu.upc.bean.GoodsCarBean;
import cn.edu.upc.bean.RepealRecordBean;
import cn.edu.upc.bean.ShopInformationBean;
import cn.edu.upc.bean.UserContextBean;
import cn.edu.upc.bean.UserWantBean;
import cn.edu.upc.pojo.AdminInformation;
import cn.edu.upc.pojo.AllKinds;
import cn.edu.upc.pojo.Classification;
import cn.edu.upc.pojo.Consumption;
import cn.edu.upc.pojo.FeedBack;
import cn.edu.upc.pojo.GoodsCar;
import cn.edu.upc.pojo.RepealRecord;
import cn.edu.upc.pojo.ShopContext;
import cn.edu.upc.pojo.ShopInformation;
import cn.edu.upc.pojo.Specific;
import cn.edu.upc.pojo.UserAddr;
import cn.edu.upc.pojo.UserContext;
import cn.edu.upc.pojo.UserInformation;
import cn.edu.upc.pojo.UserPassword;
import cn.edu.upc.pojo.UserRelease;
import cn.edu.upc.pojo.UserWant;
import cn.edu.upc.response.BaseResponse;
import cn.edu.upc.service.AdminInformationService;
import cn.edu.upc.service.AllKindsService;
import cn.edu.upc.service.ClassificationService;
import cn.edu.upc.service.ConsumptionService;
import cn.edu.upc.service.FeedBackService;
import cn.edu.upc.service.GoodsCarService;
import cn.edu.upc.service.RepealRecordService;
import cn.edu.upc.service.ShopCarService;
import cn.edu.upc.service.ShopContextService;
import cn.edu.upc.service.ShopInformationService;
import cn.edu.upc.service.SpecificeService;
import cn.edu.upc.service.UserAddrService;
import cn.edu.upc.service.UserContextService;
import cn.edu.upc.service.UserInformationService;
import cn.edu.upc.service.UserPasswordService;
import cn.edu.upc.service.UserReleaseService;
import cn.edu.upc.service.UserWantService;
import cn.edu.upc.token.TokenProccessor;
import cn.edu.upc.tool.SampleTime;
import cn.edu.upc.tool.SaveSession;
import cn.edu.upc.tool.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * 用户控制
 */
@Controller
@Slf4j
public class UserController {
	@Resource
	private AdminInformationService adminInformationService;
    @Resource
    private UserInformationService userInformationService;
    @Resource
    private UserPasswordService userPasswordService;
    @Resource
    private UserReleaseService userReleaseService;
    @Resource
    private UserAddrService userAddrService;
    @Resource
    private UserWantService userWantService;
    @Resource
    private ShopCarService shopCarService;
    @Resource
	private ConsumptionService consumptionService;
    @Resource
    private RepealRecordService repealRecordService;
    @Resource
    private ShopInformationService shopInformationService;
    @Resource
    private GoodsCarService goodsCarService;
    @Resource
    private SpecificeService specificeService;
    @Resource
    private ClassificationService classificationService;
    @Resource
    private AllKindsService allKindsService;
    @Resource
    private ShopContextService shopContextService;
    @Resource
    private UserContextService userContextService;
    @Resource
    private FeedBackService feedBackService;
//    Logger log = Logger.getLogger(this.getClass());
    
    /**
     * 主页面  进入登录界面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
    	
    	System.out.println("UserController--login--login.do--get");
    	
    	//获取 token
        String token = TokenProccessor.getInstance().makeToken();
        
        request.getSession().setAttribute("token", token);
        model.addAttribute("token", token);
        
        return "page/login_page";
    }
 
    /**
     * 登录页 登录请求验证
     * @param request
     * @param phone
     * @param password
     * @param token
     * @return
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String login(HttpServletRequest request,
                        @RequestParam String phone, @RequestParam String password, @RequestParam String token) {
    	if(!StringUtils.getInstance().isPhone(phone) && !StringUtils.getInstance().isTestPhone(phone)) {
    		return "redirect:/login.do";
    	}
    	System.out.println("UserController--login--login.do--POST");
    	
        String loginToken = (String) request.getSession().getAttribute("token");
        
        //如果账号或密码是null || ""
        if (StringUtils.getInstance().isNullOrEmpty(phone) || StringUtils.getInstance().isNullOrEmpty(password)) {
            return "redirect:/login.do";
        }
        //防止重复提交
        if (StringUtils.getInstance().isNullOrEmpty(token) || !token.equals(loginToken)) {
            return "redirect:/login.do";
        }
        boolean b = check(phone, password, request);
        
        //失败，不存在该账号
        if (!b) {
        	System.out.println("failed    phone="+phone+"\t password="+password+"\t request="+request);
            return "redirect:/login.do?msg=不存在该账号";
        }
        return "redirect:/";
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout.do")
    public String logout(HttpServletRequest request) {
    	
    	System.out.println("UserController--logout--logout.do");
    	
        try {
            UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
            SaveSession.getInstance().remove(userInformation.getPhone());
            request.getSession().removeAttribute("userInformation");
            request.getSession().removeAttribute("uid");
            System.out.println("logout");
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
        return "redirect:/";
    }
    
    /**
     * 用户注册,拥有插入数据而已
     * @param model
     * @param name
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping(value = "/registered.do", method = RequestMethod.POST)
    public String registered(Model model,
                             @RequestParam String name, @RequestParam String phone, @RequestParam String password) {
    	
    	System.out.println("UserController--registered--registered.do");
    	
        UserInformation userInformation = new UserInformation();
        userInformation.setUsername(name);
        userInformation.setPhone(phone);
        userInformation.setModified(SampleTime.getTime(new Date()));
        userInformation.setCreatetime(SampleTime.getTime(new Date()));
        
        if (userInformationService.insertSelective(userInformation) == 1) {
            int uid = userInformationService.selectIdByPhone(phone);
            UserPassword userPassword = new UserPassword();
            userPassword.setModified(SampleTime.getTime(new Date()));
            password = StringUtils.getInstance().getMD5(password);
            userPassword.setPassword(password);
            userPassword.setUid(uid);
            
            int result = userPasswordService.insertSelective(userPassword);
            if (result != 1) {
                model.addAttribute("result", "fail");
                return "success";
            }
            model.addAttribute("result", "success");
            return "success";
        }
        
        model.addAttribute("result", "fail");
        return "success";
    }

    /**
     * 查看用户基本信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/personal_info.do")
    public String personalInfo(HttpServletRequest request, Model model) {
    	
    	System.out.println("UserController--personalInfo--personal_info.do");
    	
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        //用户不存在或为null,返回登录页,重新登陆
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        
        String personalInfoToken = TokenProccessor.getInstance().makeToken();
        request.getSession().setAttribute("personalInfoToken", personalInfoToken);
        model.addAttribute("token", personalInfoToken);
        model.addAttribute("userInformation", userInformation);
        
        AdminInformation adminInformation = adminInformationService.selectByUid(userInformation.getId());
        if(!StringUtils.getInstance().isNullOrEmpty(adminInformation)) {
        	//用户反馈
        	List<FeedBack> fbl = feedBackService.findAll();
        	List<FeedBackBean> fbb = new ArrayList<>();
        	
        	for(int i = 0; i < fbl.size(); i++) {
        		FeedBack tmp = fbl.get(i);
        		FeedBackBean feed = new FeedBackBean();
        		
        		feed.setContext(tmp.getContext());
        		if(tmp.getDisplay() == 1) {
        			feed.setDisplay("未处理");
        		}else {
        			feed.setDisplay("已处理");
        		}
        		feed.setEmail(tmp.getEmail());
        		feed.setModified(tmp.getModified());
        		feed.setPhone(tmp.getPhone());
        		
        		UserInformation u = userInformationService.selectByPrimaryKey(tmp.getUid());
        		feed.setUsername(u.getUsername());
        		
        		fbb.add(feed);
        	}
        	model.addAttribute("fbb", fbb);
        	
        	//交易流水
            List<Consumption> buy = consumptionService.findAll();
            List<ConsumptionBean> ls = new LinkedList<>();
        	
            for(int i = 0; i < buy.size(); i++) {
            	ConsumptionBean consumptionBean = new ConsumptionBean();
            	int count = buy.get(i).getCounts();
            	ShopInformation x = shopInformationService.selectByPrimaryKey(buy.get(i).getSid());
            	int money = x.getPrice().multiply(new BigDecimal(count)).intValue();
            	consumptionBean.setMoney(money);
            	consumptionBean.setGoodsname(x.getName());
            	
            	String se = userInformationService.selectByPrimaryKey(buy.get(i).getSeller()).getUsername();
            	String bu = userInformationService.selectByPrimaryKey(buy.get(i).getBuyer()).getUsername();
            	
            	consumptionBean.setIo(se);     //卖家
            	consumptionBean.setTransactors(bu);   //买家
            	consumptionBean.setCounts(count);
            	consumptionBean.setModified(buy.get(i).getModified());
            	
            	ls.add(consumptionBean);
            }
            model.addAttribute("ls", ls);
        	
        	//审核记录
        	List<RepealRecord> repealRecord = repealRecordService.selectAll();
        	List<RepealRecordBean> repealRecordList = new ArrayList<>();
        	for(int i = 0; i < repealRecord.size(); i++) {
        		RepealRecord record = repealRecord.get(i);
        		
        		UserInformation auditing = userInformationService.selectByPrimaryKey(record.getAuditing());
        		UserInformation publishuser = userInformationService.selectByPrimaryKey(record.getPublishuser());
        		
        		RepealRecordBean RepealRecordBean = new RepealRecordBean();
        		RepealRecordBean.setGoodsname(record.getGoodsname());
        		RepealRecordBean.setAuditing(auditing.getUsername());
        		RepealRecordBean.setModified(record.getModified());
        		RepealRecordBean.setPublishuser(publishuser.getUsername());
        		RepealRecordBean.setReason(record.getReason());
        		
        		repealRecordList.add(RepealRecordBean);
        	}
        	
        	model.addAttribute("repealRecordList", repealRecordList);
        	
        	return "page/personal/personal_info_admin";
        }
        
        List<Consumption> buy = consumptionService.findBuyer(userInformation.getId());
        List<Consumption> seller = consumptionService.findSeller(userInformation.getId());
        
        List<ConsumptionBean> ls = new LinkedList<>();
        
        for(int i = 0; i < buy.size(); i++) {
        	ConsumptionBean consumptionBean = new ConsumptionBean();
        	int count = buy.get(i).getCounts();
        	ShopInformation x = shopInformationService.selectByPrimaryKey(buy.get(i).getSid());
        	int money = x.getPrice().multiply(new BigDecimal(count)).intValue();
        	consumptionBean.setMoney(money);
        	consumptionBean.setGoodsname(x.getName());
        	consumptionBean.setIo("买入");
        	String uu = userInformationService.selectByPrimaryKey(buy.get(i).getSeller()).getUsername();
        	consumptionBean.setTransactors(uu);  //卖家
        	consumptionBean.setCounts(count);
        	consumptionBean.setModified(buy.get(i).getModified());
        	
        	ls.add(consumptionBean);
        }
        System.out.println("seller.size()="+seller.size());
        for(int i = 0; i < seller.size(); i++) {
        	ConsumptionBean consumptionBean = new ConsumptionBean();
        	int count = seller.get(i).getCounts();
        	ShopInformation x = shopInformationService.selectByPrimaryKey(seller.get(i).getSid());
        	int money = x.getPrice().multiply(new BigDecimal(count)).intValue();
        	consumptionBean.setMoney(money);
        	consumptionBean.setGoodsname(x.getName());
        	consumptionBean.setIo("卖出");
        	String uu = userInformationService.selectByPrimaryKey(seller.get(i).getBuyer()).getUsername();
        	consumptionBean.setTransactors(uu);   //买家
        	consumptionBean.setCounts(count);
        	consumptionBean.setModified(seller.get(i).getModified());
        	
        	ls.add(consumptionBean);
        }
        model.addAttribute("ls", ls);
        
        //用户查看自己商品的审核反馈
        List<RepealRecord> repealRecord = repealRecordService.selectAll();
        List<RepealRecordBean> repealRecordList = new ArrayList<>();
        for(int i = 0; i < repealRecord.size(); i++) {
        	RepealRecord record = repealRecord.get(i);
        	if(record.getPublishuser() == userInformation.getId()) {
        		UserInformation Auditing = userInformationService.selectByPrimaryKey(record.getAuditing());
        		
        		RepealRecordBean RepealRecordBean = new RepealRecordBean();
        		RepealRecordBean.setGoodsname(record.getGoodsname());
        		RepealRecordBean.setAuditing(Auditing.getUsername());
        		RepealRecordBean.setModified(record.getModified());
        		RepealRecordBean.setPublishuser(userInformation.getUsername());
        		RepealRecordBean.setReason(record.getReason());
        		
        		repealRecordList.add(RepealRecordBean);
        	}
        }
        
    	model.addAttribute("repealRecordList", repealRecordList);
        return "page/personal/personal_info";
    }
    
    @RequestMapping(value="/feed_back.do")
    public String feedBack(HttpServletRequest request, Model model) {
    	UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    	
    	//没有登录，跳转到登录页
    	if(StringUtils.getInstance().isNullOrEmpty(userInformation)) {
    		return "redirect:/login.do";
    	}
    	
    	model.addAttribute("userInformation", userInformation);
    	
    	
    	
    	return "page/personal/feedback";
    }
    
    @RequestMapping(value="/fbaccept.do", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse feedBackAccept(HttpServletRequest request, @RequestParam String area, 
    		@RequestParam String phone, @RequestParam String email) {
    	
    	System.out.println("UserController--feedBackAccept--feed_back_accept.do");
    	
    	UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    	
    	FeedBack feedBack = new FeedBack();
    	feedBack.setContext(area);
    	feedBack.setDisplay(1);
    	feedBack.setEmail(email);
    	feedBack.setEmail(phone);
    	feedBack.setModified(SampleTime.getTime(new Date()));
    	feedBack.setUid(userInformation.getId());
    	
    	int res = feedBackService.insert(feedBack);
    	
    	if(res == 1) {
    		return BaseResponse.success();
    	}
    	return BaseResponse.fail();
    }
    
    /**
     * 显示我的消息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="/my_record.do")
    public String myRecord(HttpServletRequest request, Model model) {
    	
    	System.out.println("UserController--myRecord--myRecord.do");
		 
		UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
		
		if(StringUtils.getInstance().isNullOrEmpty(userInformation)) {
			return "redirect:/login.do";
		}
		
		model.addAttribute("userInformation", userInformation);
		List<UserContext> contextList = userContextService.accepter(userInformation.getId());
		
		UserContextBean pl0 = new UserContextBean();
		UserContextBean pl1 = new UserContextBean();
    	UserContextBean pl2 = new UserContextBean();
    	UserContextBean pl3 = new UserContextBean();
    	UserContextBean pl4 = new UserContextBean();
    	UserContextBean pl5 = new UserContextBean();
    	UserContextBean pl6 = new UserContextBean();
    	UserContextBean pl7 = new UserContextBean();
    	
    	if(contextList.size() <= 0) {
    		pl0.setUsername("您暂时没有消息呢");
    	}

    	if(contextList.size() >= 1) {
    		int sendid = contextList.get(0).getSendid();
    		UserInformation sender = userInformationService.selectByPrimaryKey(sendid);
    		pl1.setUsername(sender.getUsername());
    		pl1.setModified(contextList.get(0).getModified());
    		pl1.setContext(contextList.get(0).getContext());
    		pl1.setSendid(contextList.get(0).getSendid()+"-"+contextList.get(0).getId());
    	}
    	
    	if(contextList.size() >= 2) {
    		int sendid = contextList.get(1).getSendid();
    		UserInformation sender = userInformationService.selectByPrimaryKey(sendid);
    		pl2.setUsername(sender.getUsername());
    		pl2.setModified(contextList.get(1).getModified());
    		pl2.setContext(contextList.get(1).getContext());
    		pl2.setSendid(contextList.get(1).getSendid()+"-"+contextList.get(0).getId());
    	}
    	if(contextList.size() >= 3) {
    		int sendid = contextList.get(2).getSendid();
    		UserInformation sender = userInformationService.selectByPrimaryKey(sendid);
    		pl3.setUsername(sender.getUsername());
    		pl3.setModified(contextList.get(2).getModified());
    		pl3.setContext(contextList.get(2).getContext());
    		pl3.setSendid(contextList.get(2).getSendid()+"-"+contextList.get(0).getId());
    	}
    	if(contextList.size() >= 4) {
    		int sendid = contextList.get(3).getSendid();
    		UserInformation sender = userInformationService.selectByPrimaryKey(sendid);
    		pl4.setUsername(sender.getUsername());
    		pl4.setModified(contextList.get(3).getModified());
    		pl4.setContext(contextList.get(3).getContext());
    		pl4.setSendid(contextList.get(3).getSendid()+"-"+contextList.get(0).getId());
    	}
    	
    	if(contextList.size() >= 5) {
    		int sendid = contextList.get(4).getSendid();
    		UserInformation sender = userInformationService.selectByPrimaryKey(sendid);
    		pl5.setUsername(sender.getUsername());
    		pl5.setModified(contextList.get(4).getModified());
    		pl5.setContext(contextList.get(4).getContext());
    		pl5.setSendid(contextList.get(4).getSendid()+"-"+contextList.get(0).getId());
    	}
    	
    	if(contextList.size() >= 6) {
    		int sendid = contextList.get(5).getSendid();
    		UserInformation sender = userInformationService.selectByPrimaryKey(sendid);
    		pl6.setUsername(sender.getUsername());
    		pl6.setModified(contextList.get(5).getModified());
    		pl6.setContext(contextList.get(5).getContext());
    		pl6.setSendid(contextList.get(5).getSendid()+"-"+contextList.get(0).getId());
    	}
    	
    	if(contextList.size() >= 7) {
    		int sendid = contextList.get(6).getSendid();
    		UserInformation sender = userInformationService.selectByPrimaryKey(sendid);
    		pl7.setUsername(sender.getUsername());
    		pl7.setModified(contextList.get(6).getModified());
    		pl7.setContext(contextList.get(6).getContext());
    		pl7.setSendid(contextList.get(6).getSendid()+"-"+contextList.get(0).getId());
    	}
    	
    	model.addAttribute("pl0", pl0);
    	model.addAttribute("pl1", pl1);
    	model.addAttribute("pl2", pl2);
    	model.addAttribute("pl3", pl3);
    	model.addAttribute("pl4", pl4);
    	model.addAttribute("pl5", pl5);
    	model.addAttribute("pl6", pl6);
    	model.addAttribute("pl7", pl7);
		
    	return "page/personal/my_record";
    }
    
    /**
     * 我的消息  回复
     * @param request
     * @return
     */
    @RequestMapping(value="/recordback.do", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse recordback(HttpServletRequest request, @RequestParam String txt,
    		@RequestParam String acc) {
    	
    	System.out.println("UserController--recordback--recordback.do--post---acc="+acc);
    	UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    	
    	String accept = acc.split("-")[0];
    	String ids = acc.split("-")[1];
  
    	//将消息从待回复列表中去掉
    	UserContext u = new UserContext();
    	u.setId(Integer.parseInt(ids));
    	u.setStat("1");
    	userContextService.updateStat(u);
    	
    	//发送一条回复
    	UserContext userContext = new UserContext();
    	userContext.setAcceptid(Integer.parseInt(accept));
    	userContext.setContext(txt);
    	userContext.setModified(SampleTime.getTime(new Date()));
    	userContext.setSendid(userInformation.getId());
    	int res = userContextService.insert(userContext);
    	
    	if(res == 1) {
    		return BaseResponse.success();
    	}
    	return BaseResponse.fail();
    }
    
	  /**
	  * 完善用户基本信息，认证
	  * @param request
	  * @param data  
	  * @return    0 失败   1 成功
	  */
	 @RequestMapping(value = "/certification.do", method = RequestMethod.POST)
	 @ResponseBody
    public BaseResponse certification(HttpServletRequest request, @RequestParam String arr) {

		 System.out.println("UserController--certification--certification.do--post");
		 
		 System.out.println("UserController--certification--certification.do--post--data="+arr);
		 UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
		 String key = arr.split("-")[0];
		 String val = arr.split("-")[1];
		 
		 userInformation.setModified(SampleTime.getTime(new Date()));
		 
		 switch(key) {
		 	case "userName": 
		 		userInformation.setUsername(val);
		 		break;
		 	case "realName": 
		 		userInformation.setRealname(val);
		 		break;
		 	case "sex": 
		 		userInformation.setGender(val);
		 		break;
		 	case "clazz": 
		 		userInformation.setClazz(val);
		 		break;
		 	case "dormitory": 
		 		userInformation.setDormitory(val);
		 		break;
		 	case "sno": 
		 		userInformation.setSno(val);
		 		break;
		 	case "email": 
		 		userInformation.setEmail(val);
		 		break;
		 	default:
	 			System.out.println("其他信息");
	 			break;
		 }
		 int result;
		 result = userInformationService.updateByPrimaryKey(userInformation);
		 if(result == 1) {
			 System.out.println("用户信息, 更新成功！");
			 return BaseResponse.success(); 
		 }
		 else {
			 System.out.println("用户信息, 更新失败！");
			 return BaseResponse.fail(); 
		 }
    }

    /**
     * 进入求购页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/require_product.do")
    public String enterPublishUserWant(HttpServletRequest request, Model model) {
    	
    	System.out.println("UserController--enterPublishUserWant--require_product.do");
    	
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        String error = request.getParameter("error");
        if (!StringUtils.getInstance().isNullOrEmpty(error)) {
            model.addAttribute("error", "error");
        }
        String publishUserWantToken = TokenProccessor.getInstance().makeToken();
        request.getSession().setAttribute("publishUserWantToken", publishUserWantToken);
        model.addAttribute("token", publishUserWantToken);
        model.addAttribute("userInformation", userInformation);
        return "page/require_product";
    }

    /**
     * 修改求购商品
     * @param request
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/modified_require_product.do")
    public String modifiedRequireProduct(HttpServletRequest request, Model model,
                                         @RequestParam int id) {
    	
    	System.out.println("UserController--modifiedRequireProduct--modified_require_product.do");
    	
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        String publishUserWantToken = TokenProccessor.getInstance().makeToken();
        request.getSession().setAttribute("publishUserWantToken", publishUserWantToken);
        model.addAttribute("token", publishUserWantToken);
        model.addAttribute("userInformation", userInformation);
        UserWant userWant = userWantService.selectByPrimaryKey(id);
        model.addAttribute("userWant", userWant);
        String sort = getSort(userWant.getSort());
        model.addAttribute("sort", sort);
        return "page/modified_require_product";
    }
    
    /**
     * 求购---发送留言jhl
     * @return
     */
    @RequestMapping(value="/send.do", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse send(HttpServletRequest request, @RequestParam String ly, @RequestParam int id) {
    	
    	System.out.println("send--send.do---"+id);
    	UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    	if(StringUtils.getInstance().isNullOrEmpty(userInformation)) {
    		return BaseResponse.fail("请先登录.....");
    	}
    	if(StringUtils.getInstance().isNullOrEmpty(userInformation.getUsername())) {
    		return BaseResponse.fail("请先登录..");
    	}
    	UserWant userWant = userWantService.selectByPrimaryKey(id);
    	
    	UserContext userContext = new UserContext();
    	userContext.setAcceptid(userWant.getUid());   //商品的求购者，也就是消息接收者
    	userContext.setContext(ly);
    	userContext.setModified(SampleTime.getTime(new Date()));
    	userContext.setSendid(userInformation.getId());
    	
    	
    	int result = userContextService.insert(userContext);
    	
    	if(result == 1) {
    		return BaseResponse.success();
    	}
    	
    	return BaseResponse.fail();
    }
    
    /**
     * 发布---发送留言
     * @return
     */
    @RequestMapping(value="/sendP.do", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse sendP(HttpServletRequest request, @RequestParam String ly, @RequestParam int id) {
    	
    	System.out.println("sendP--sendP.do---"+id);
    	UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    	if(StringUtils.getInstance().isNullOrEmpty(userInformation) || StringUtils.getInstance().isNullOrEmpty(userInformation.getUsername())) {
    		return BaseResponse.fail("请先登录..");
    	}
    	ShopInformation  shopInfo = shopInformationService.selectByPrimaryKey(id);
    	
    	UserContext userContext = new UserContext();
    	userContext.setAcceptid(shopInfo.getUid());   //商品的发布者，也就是消息接收者
    	userContext.setContext(ly);
    	userContext.setModified(SampleTime.getTime(new Date()));
    	userContext.setSendid(userInformation.getId());
    	
    	
    	int result = userContextService.insert(userContext);
    	
    	if(result == 1) {
    		return BaseResponse.success();
    	}
    	
    	return BaseResponse.fail();
    }
    
    /**
     * 发布求购
     * @param request
     * @param model
     * @param name
     * @param sort
     * @param quantity
     * @param price
     * @param remark
     * @param token
     * @return
     */
    @RequestMapping(value = "/publishUserWant.do", method = RequestMethod.POST)
    public String publishUserWant(HttpServletRequest request, Model model,
                                  @RequestParam String name,
                                  @RequestParam int sort, @RequestParam int quantity,
                                  @RequestParam double price, @RequestParam String remark,
                                  @RequestParam String token) {
    	
    	System.out.println("UserController--publishUserWant--publishUserWant.do");
    	System.out.println("-----------------------"+name);
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        String publishUserWantToke = (String) request.getSession().getAttribute("publishUserWantToken");
        if (StringUtils.getInstance().isNullOrEmpty(publishUserWantToke) || !publishUserWantToke.equals(token)) {
            return "redirect:require_product.do?error=3";
        } else {
            request.getSession().removeAttribute("publishUserWantToken");
        }

        try {
            if (name.length() < 1 || remark.length() < 1 || name.length() > 25 || remark.length() > 25) {
                return "redirect:require_product.do";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:require_product.do?error=1";
        }
        UserWant userWant = new UserWant();
        userWant.setModified(SampleTime.getTime(new Date()));
        userWant.setName(name);
        userWant.setPrice(new BigDecimal(price));
        userWant.setQuantity(quantity);
        userWant.setRemark(remark);
        userWant.setUid(userInformation.getId());
        userWant.setSort(sort);
//        System.out.println(userInformation.getId());
        int result;
        try {
            result = userWantService.insertSelective(userWant);
            if (result != 1) {
                return "redirect:/require_product.do?error=2";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/require_product.do?error=2";
        }
        return "redirect:/my_require_product.do";
    }

    /**
     * getUserWant,查看我的求购
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/my_require_product.do", "/my_require_product_page.do"})
    public String getUserWant(HttpServletRequest request, Model model) {
    	
    	System.out.println("UserController--getUserWant--my_require_product.do");
    	
        List<UserWant> list;
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        try {
        	int uid = userInformation.getId();
            list = selectUserWantByUid(uid);
            List<UserWantBean> userWantBeans = new ArrayList<>();
            for (UserWant userWant : list) {
                UserWantBean userWantBean = new UserWantBean();
                userWantBean.setId(userWant.getId());
                userWantBean.setModified(userWant.getModified());
                userWantBean.setName(userWant.getName());
                userWantBean.setPrice(userWant.getPrice().doubleValue());
                userWantBean.setUid(uid);
                userWantBean.setQuantity(userWant.getQuantity());
                userWantBean.setRemark(userWant.getRemark());
                userWantBean.setSort(getSort(userWant.getSort()));
                userWantBeans.add(userWantBean);
            }
            model.addAttribute("userWant", userWantBeans);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        model.addAttribute("userInformation", userInformation);
        return "page/personal/my_require_product_page";
    }

    /**
     * getUserWantCounts.do,查看求购总数
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getUserWantCounts.do")
    @ResponseBody
    public Map<String, Integer> getUserWantCounts(HttpServletRequest request, Model model) {
        Map<String, Integer> map = new HashMap<>();
        if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
            map.put("counts", -1);
            return map;
        }
        try {
            int counts = getUserWantCounts((Integer) request.getSession().getAttribute("uid"));
            map.put("counts", counts);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("counts", -1);
        }
        return map;
    }

    /**
     * 删除求购
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteUserWant.do")
    public String deleteUserWant(HttpServletRequest request, @RequestParam int id) {
        if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
            return "redirect:/login.do";
        }
        UserWant userWant = new UserWant();
        userWant.setId(id);
        userWant.setDisplay(0);
        try {
            int result = userWantService.updateByPrimaryKeySelective(userWant);
            if (result != 1) {
                return "redirect:my_require_product.do";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:my_require_product.do";
    }

    /**
     * 购物车开始
     * @param request
     * @return
     */
    @RequestMapping(value = "/getShopCarCounts.do")
    @ResponseBody
    public BaseResponse getShopCarCounts(HttpServletRequest request) {
        if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
            return BaseResponse.fail();
        }

        return BaseResponse.success();
    }

    /**
     * check the shopping cart,查看购物车
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/shopping_cart.do")
    public String selectShopCar(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            userInformation = new UserInformation();
            model.addAttribute("userInformation", userInformation);
            return "redirect:/login.do";
        } else {
            model.addAttribute("userInformation", userInformation);
        }
        
        UserAddr userAddr = userAddrService.findById(userInformation.getId());
        
        if(StringUtils.getInstance().isNullOrEmpty(userAddr)) {
        	userAddr = new UserAddr();
        	userAddr.setAddr("无");
        	userAddr.setPhone("11110000");
        }
        
        model.addAttribute("userAddr", userAddr);
        
        System.out.println("UserController -- selectShopCar --  addr="+userAddr.getAddr()+"   phone="+userAddr.getPhone());
        
        int uid = userInformation.getId();
        List<GoodsCar> goodsCars = goodsCarService.selectByUid(uid);
        List<GoodsCarBean> goodsCarBeans = new ArrayList<>();
        
        
        for (GoodsCar goodsCar : goodsCars) {
            GoodsCarBean goodsCarBean = new GoodsCarBean();
            goodsCarBean.setUid(goodsCar.getUid());
            goodsCarBean.setSid(goodsCar.getSid());
            goodsCarBean.setModified(goodsCar.getModified());
            goodsCarBean.setId(goodsCar.getId());
            goodsCarBean.setQuantity(goodsCar.getQuantity());
            System.out.println("UserController -- selectShopCar -- goodsCar.getSid()="+goodsCar.getSid());
            
            ShopInformation shopInformation = shopInformationService.selectByPrimaryKey(goodsCar.getSid());
            
            if(StringUtils.getInstance().isNullOrEmpty(shopInformation)) {
            	System.out.println("UserController -- selectShopCar 商品数据丢失");
            }
            
            goodsCarBean.setName(shopInformation.getName());
            goodsCarBean.setRemark(shopInformation.getRemark());
            goodsCarBean.setImage(shopInformation.getImage());
            goodsCarBean.setPrice(shopInformation.getPrice().doubleValue());
            goodsCarBean.setSort(getSort(shopInformation.getSort()));
            goodsCarBeans.add(goodsCarBean);
        }
        model.addAttribute("list", goodsCarBeans);
        
        return "page/shopping_cart";
    }

    /**
     * 添加到购物车
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/insertGoodsCar.do")
    @ResponseBody
    public BaseResponse insertGoodsCar(HttpServletRequest request, @RequestParam int id) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        
        System.out.println("UserController--insertGoodsCar--insertGoodsCar.do");
        
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return BaseResponse.fail();
        }
        int uid = userInformation.getId();
        GoodsCar goodsCar = new GoodsCar();
        goodsCar.setDisplay(1);
        goodsCar.setModified(SampleTime.getTime(new Date()));
        goodsCar.setQuantity(1);
        goodsCar.setUid(uid);
        goodsCar.setSid(id);
        int result = goodsCarService.insertSelective(goodsCar);
        if(result >= 1) {
        	return BaseResponse.success();
        } else {
        	return BaseResponse.fail(); 
        }
        
    }
    
    /**
     * 撤销商品  删除商品
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteGoods.do")
    @ResponseBody
    public BaseResponse deleteGoods(HttpServletRequest request, @RequestParam String id, @RequestParam String reason) {
    	
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        
        System.out.println("UserController--deleteGoods--deleteGoods.do");
        Integer sid = Integer.parseInt(id);
        //没有登录
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return BaseResponse.fail(2);
        }
        
        int uid = userInformation.getId();
        AdminInformation adminInformation = adminInformationService.selectByUid(userInformation.getId());
        
        //如果不是管理员, 返回
        if(StringUtils.getInstance().isNullOrEmpty(adminInformation)) {
        	return BaseResponse.fail(3);
        }
        
        ShopInformation shopInformation = shopInformationService.selectByPrimaryKey(sid);
        
        //获取撤销记录对象
        RepealRecord repealRecord = new RepealRecord();
        
        repealRecord.setGoodsname(shopInformation.getName());
        repealRecord.setPublishuser(shopInformation.getUid());
        repealRecord.setAuditing(uid);
        repealRecord.setReason(reason);
        repealRecord.setModified(SampleTime.getTime(new Date()));
        
        
        int result = 1;
        //数据库操作
        result = repealRecordService.insertRecord(repealRecord);
        
        ShopInformation shopInformationB = new ShopInformation();
        shopInformationB.setModified(SampleTime.getTime(new Date()));
        shopInformationB.setDisplay(0);
        shopInformationB.setId(sid);
        int shop = shopInformationService.updateByPrimaryKeySelective(shopInformationB);
        
        if(result == 1 & shop == 1) {
        	result = 1;
        }
        
        if(result >= 1) {
        	return BaseResponse.success();
        } else {
        	return BaseResponse.fail(); 
        }
        
    }

    /**
     * 删除购物车的商品
     * @param request
     * @param id
     * @param sid
     * @return
     */
    @RequestMapping(value = "/deleteShopCar.do")
    @ResponseBody
    public BaseResponse deleteShopCar(HttpServletRequest request, @RequestParam int id, @RequestParam int sid) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        
        System.out.println("UserController--deleteShopCar--deleteShopCar.do");
        
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return BaseResponse.fail();
        }
        int uid = userInformation.getId();
        GoodsCar goodsCar = new GoodsCar();
        goodsCar.setDisplay(0);
        goodsCar.setId(id);
        goodsCar.setSid(sid);
        goodsCar.setUid(uid);
        int result = goodsCarService.updateByPrimaryKeySelective(goodsCar);
        if (result != 1) {
            return BaseResponse.fail();
        }
        return BaseResponse.success();
    }
    
    /**
     * 地址信息修改
     * @param request
     * @param id
     * @param sid
     * @return
     */
    @RequestMapping(value = "/address.do")
    @ResponseBody
    public BaseResponse address(HttpServletRequest request, @RequestParam String address) {

    	System.out.println("UserController--address--address.do--"+address);
    	
    	UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    	
    	//获取uid对用的用户地址
    	UserAddr find = userAddrService.findById(userInformation.getId());
    	int result;
    	
    	if(StringUtils.getInstance().isNullOrEmpty(find)) {
    		UserAddr userAddr = new UserAddr();
        	userAddr.setUid(userInformation.getId());
        	userAddr.setAddr(address);
        	userAddr.setPhone(userInformation.getPhone());
    		
    		//如果用户地址信息不存在, 就插入
    		result = userAddrService.insertAddr(userAddr);
    	} else {
    		find.setAddr(address);
        	//更新数据库
    		result = userAddrService.updateAddr(find);
    	}

    	if(result == 1) {
    		return BaseResponse.success();
    	}
    	else {
    		return BaseResponse.fail();
    	}
    }
    
    
    /**
     * 发布商品
     * @param name
     * @param level
     * @param remark
     * @param price
     * @param sort
     * @param quantity
     * @param token
     * @param image
     * @param action
     * @param id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/insertGoods.do", method = RequestMethod.POST)
    public String insertGoods(@RequestParam String name, @RequestParam int level,
                              @RequestParam String remark, @RequestParam double price,
                              @RequestParam int sort, @RequestParam int quantity,
                              @RequestParam String token, @RequestParam(required = false) MultipartFile image,
                              @RequestParam int action, @RequestParam(required = false) int id,
                              HttpServletRequest request, Model model) {
    	
    	System.out.println("UserController--insertGoods--insertGoods.do");
    	
        String goodsToken = (String) request.getSession().getAttribute("goodsToken");

        //防止重复提交
        if (StringUtils.getInstance().isNullOrEmpty(goodsToken) || !goodsToken.equals(token)) {
            return "redirect:publish_product.do?error=1";
        } else {
            request.getSession().removeAttribute("goodsToken");
        }
        
        //从session中获得用户的基本信息
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        model.addAttribute("userInformation", userInformation);
        
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            //如果用户不存在，
            return "redirect:/login.do";
        }
        name = StringUtils.getInstance().replaceBlank(name);
        remark = StringUtils.getInstance().replaceBlank(remark);
        
        //数据格式判断
        if (StringUtils.getInstance().isNullOrEmpty(name) || StringUtils.getInstance().isNullOrEmpty(level) || StringUtils.getInstance().isNullOrEmpty(remark) || StringUtils.getInstance().isNullOrEmpty(price)
                || StringUtils.getInstance().isNullOrEmpty(sort) || StringUtils.getInstance().isNullOrEmpty(quantity) || name.length() > 25 || remark.length() > 122) {
            model.addAttribute("message", "请输入正确的格式!!!!!");
            model.addAttribute("token", goodsToken);
            request.getSession().setAttribute("goodsToken", goodsToken);
            return "publish_product";
        }
        
        //插入
        if (action == 1) {
            if (StringUtils.getInstance().isNullOrEmpty(image)) {
                model.addAttribute("message", "请选择图片!!!");
                model.addAttribute("token", goodsToken);
                request.getSession().setAttribute("goodsToken", goodsToken);
                return "redirect:publish_product.do?error=请插入图片";
            }
            String random;
            
            //已建立虚拟路径  D:/image     ->   /image
            String path = "D:";
            String save = "";
            String tamp= System.currentTimeMillis() + ".jpg";
            random = "/image/" + tamp;           
            StringBuilder thumbnails = new StringBuilder();
            thumbnails.append(path);
            thumbnails.append("image/thumbnails/");   //  D:/image/thumbnails/

            File file = new File(path, random);	  //   D:/       image/tmp.jpg
            if (!file.exists()) {
            	//文件路径不存在, 创建多级目录
                file.mkdirs();   
            }
            try {
            	//使用transferTo（dest）方法将上传文件写到服务器上指定的文件。
                image.transferTo(file);		//   D:/image/tmp.jpg
            } catch (Exception e) {
                e.printStackTrace();
            }

            //创建缩略图文件夹
            File thumbnailsFile = new File(thumbnails.toString());   // D:/image/thumbnails/
            if (!thumbnailsFile.exists()) {
            	//文件路径不存在, 创建多级目录
                thumbnailsFile.mkdirs();
            }
            
            // D:/image/tmp.jpg     D:/image/thumbnails/tmp.jpg
            if (StringUtils.getInstance().thumbnails(path + random, thumbnails.toString() + tamp)) { 
                save = "/image/thumbnails/" + tamp; 	//    /image/thumbnails/tmp.jpg
            } else {
                return "redirect:publish_product.do?error=生成缩略图失败";
            }
            //商品信息
            ShopInformation shopInformation = new ShopInformation();
            shopInformation.setName(name);
            shopInformation.setLevel(level);
            shopInformation.setRemark(remark);
            shopInformation.setPrice(new BigDecimal(price));
            shopInformation.setSort(sort);
            shopInformation.setQuantity(quantity);
            shopInformation.setModified(SampleTime.getTime(new Date()));
            shopInformation.setImage(random);		//原图
            shopInformation.setThumbnails(save);	//缩略图

            int uid = (int) request.getSession().getAttribute("uid");
            shopInformation.setUid(uid);
            try {
                int result = shopInformationService.insertSelective(shopInformation);
                //插入失败
                if (result != 1) {
                    model.addAttribute("message", "请输入正确的格式!!!!!");
                    model.addAttribute("token", goodsToken);
                    request.getSession().setAttribute("goodsToken", goodsToken);
                    return "page/publish_product";
                }
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("token", goodsToken);
                model.addAttribute("message", "请输入正确的格式!!!!!");
                request.getSession().setAttribute("goodsToken", goodsToken);
                return "page/publish_product";
            }
            
            int sid = shopInformationService.selectIdByImage(random);
            
            //将发布的商品的编号插入到用户的发布中
            UserRelease userRelease = new UserRelease();
            userRelease.setModified(SampleTime.getTime(new Date()));
            userRelease.setSid(sid);
            userRelease.setUid(uid);
            try {
                int result = userReleaseService.insertSelective(userRelease);
                //如果关联失败，删除对应的商品和商品图片
                if (result != 1) {
                    //插入失败，回滚
                    shopInformationService.deleteByPrimaryKey(sid);
                    model.addAttribute("token", goodsToken);
                    model.addAttribute("message", "请输入正确的格式!!!!!");
                    request.getSession().setAttribute("goodsToken", goodsToken);
                    return "page/publish_product";
                }
            } catch (Exception e) {
                //插入失败, 回滚
                shopInformationService.deleteByPrimaryKey(sid);
                e.printStackTrace();
                model.addAttribute("token", goodsToken);
                model.addAttribute("message", "请输入正确的格式!!!!!");
                request.getSession().setAttribute("goodsToken", goodsToken);
                return "page/publish_product";
            }
            
            shopInformation.setId(sid);
            goodsToken = TokenProccessor.getInstance().makeToken();
            request.getSession().setAttribute("goodsToken", goodsToken);
            model.addAttribute("token", goodsToken);
            model.addAttribute("shopInformation", shopInformation);
            model.addAttribute("userInformation", userInformation);
            String sb = getSort(sort);
            model.addAttribute("sort", sb);
            model.addAttribute("action", 2);
            return "redirect:/my_publish_product_page.do";
        } else if (action == 2) {//更新商品
            ShopInformation shopInformation = new ShopInformation();
            shopInformation.setModified(SampleTime.getTime(new Date()));
            shopInformation.setQuantity(quantity);
            shopInformation.setSort(sort);
            shopInformation.setPrice(new BigDecimal(price));
            shopInformation.setRemark(remark);
            shopInformation.setLevel(level);
            shopInformation.setName(name);
            shopInformation.setId(id);
            try {
                int result = shopInformationService.updateByPrimaryKeySelective(shopInformation);
                if (result != 1) {
                    return "redirect:publish_product.do";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:publish_product.do";
            }
            goodsToken = TokenProccessor.getInstance().makeToken();
            request.getSession().setAttribute("goodsToken", goodsToken);
            model.addAttribute("token", goodsToken);
            shopInformation = shopInformationService.selectByPrimaryKey(id);
            model.addAttribute("userInformation", userInformation);
            model.addAttribute("shopInformation", shopInformation);
            model.addAttribute("action", 2);
            model.addAttribute("sort", getSort(sort));
        }
        return "redirect:/my_publish_product_page.do";
    }

    /**
     * 从发布的商品直接跳转到修改商品
     * @param request
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/modifiedMyPublishProduct.do")
    public String modifiedMyPublishProduct(HttpServletRequest request, Model model,
                                           @RequestParam int id) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        String goodsToken = TokenProccessor.getInstance().makeToken();
        request.getSession().setAttribute("goodsToken", goodsToken);
        model.addAttribute("token", goodsToken);
        ShopInformation shopInformation = shopInformationService.selectByPrimaryKey(id);
        model.addAttribute("userInformation", userInformation);
        model.addAttribute("shopInformation", shopInformation);
        model.addAttribute("action", 2);
        model.addAttribute("sort", getSort(shopInformation.getSort()));
        return "page/publish_product";
    }

    /**
     * 发表商品留言
     * @param id
     * @param context
     * @param token
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertShopContext.do")
    @ResponseBody
    public Map<String, String> insertShopContext(@RequestParam int id, @RequestParam String context, @RequestParam String token,
                                 HttpServletRequest request) {
        String goodsToken = (String) request.getSession().getAttribute("goodsToken");
        Map<String, String> map = new HashMap<>();
        map.put("result", "1");
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            map.put("result", "2");
            return map;
        }
        if (StringUtils.getInstance().isNullOrEmpty(goodsToken) || !token.equals(goodsToken)) {
            return map;
        }
        ShopContext shopContext = new ShopContext();
        shopContext.setContext(context);
        String date = SampleTime.getTime(new Date());
        shopContext.setModified(date);
        shopContext.setSid(id);
        int uid = (int) request.getSession().getAttribute("uid");
        shopContext.setUid(uid);
        try {
            int result = shopContextService.insertSelective(shopContext);
            if (result != 1) {
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        map.put("result", "1");
        map.put("username", userInformation.getUsername());
        map.put("context", context);
        map.put("time", date);
        return map;
    }

    /**
     * 删除我的商品发布，删除图标链接
     * @param request
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteShop.do")
    public String deleteShop(HttpServletRequest request, Model model, @RequestParam int id) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        } else {
            model.addAttribute("userInformation", userInformation);
        }
        ShopInformation shopInformation = new ShopInformation();
        shopInformation.setModified(SampleTime.getTime(new Date()));
        shopInformation.setDisplay(0);
        shopInformation.setId(id);
        try {
            int result = shopInformationService.updateByPrimaryKeySelective(shopInformation);
            if (result != 1) {
                return "redirect:my_publish_product_page.do";
            }
            return "redirect:my_publish_product_page.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:my_publish_product_page.do";
        }
    }

    /**
     * 查看发布的所有商品总数
     * @param request
     * @return
     */
    @RequestMapping(value = "/getReleaseShopCounts.do")
    @ResponseBody
    public Map<String, Integer> getReleaseShopCounts(HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<>();
        if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
            map.put("counts", -1);
            return map;
        }
        int counts = getReleaseCounts((Integer) request.getSession().getAttribute("uid"));
        map.put("counts", counts);
        return map;
    }

    /**
     * 查看我的发布的商品
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/my_publish_product_page.do")
    public String getReleaseShop(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        } else {
            model.addAttribute("userInformation", userInformation);
        }
        int uid = (int) request.getSession().getAttribute("uid");
        List<ShopInformation> shopInformations = shopInformationService.selectUserReleaseByUid(uid);
        List<ShopInformationBean> list = new ArrayList<>();
        String stringBuffer;

        for (ShopInformation shopInformation : shopInformations) {
            stringBuffer = getSort(shopInformation.getSort());
            ShopInformationBean shopInformationBean = new ShopInformationBean();
            shopInformationBean.setId(shopInformation.getId());
            shopInformationBean.setName(shopInformation.getName());
            shopInformationBean.setLevel(shopInformation.getLevel());
            shopInformationBean.setPrice(shopInformation.getPrice().doubleValue());
            shopInformationBean.setRemark(shopInformation.getRemark());
            shopInformationBean.setSort(stringBuffer);
            shopInformationBean.setQuantity(shopInformation.getQuantity());
            shopInformationBean.setTransaction(shopInformation.getTransaction());
            shopInformationBean.setUid(shopInformation.getUid());
            shopInformationBean.setImage(shopInformation.getImage());
            list.add(shopInformationBean);
        }
        model.addAttribute("shopInformationBean", list);
        return "page/personal/my_publish_product_page";
    }

    /**
     * 更新商品信息
     * @param sort
     * @return
     */
    private String getSort(int sort) {
        StringBuilder sb = new StringBuilder();
        Specific specific = selectSpecificBySort(sort);
        int cid = specific.getCid();
        Classification classification = selectClassificationByCid(cid);
        int aid = classification.getAid();
        AllKinds allKinds = selectAllKindsByAid(aid);
        sb.append(allKinds.getName());
        sb.append("-");
        sb.append(classification.getName());
        sb.append("-");
        sb.append(specific.getName());
        return sb.toString();
    }

    /**
     * 查看用户发布的货物的总数
     * @param uid
     * @return
     */
    private int getReleaseCounts(int uid) {
        try {
            return userReleaseService.getCounts(uid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 查看用户的求购总个数
     * @param uid
     * @return
     */
    private int getUserWantCounts(int uid) {
        try {
            return userWantService.getCounts(uid);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 求购列表10
     * @param uid
     * @return
     */
    private List<UserWant> selectUserWantByUid(int uid) {
        try {
            return userWantService.selectMineByUid(uid);
        } catch (Exception e) {
            e.printStackTrace();
            List<UserWant> list = new ArrayList<>();
            list.add(new UserWant());
            return list;
        }
    }

    /**
     * 判断该账号及其密码是否正确匹配
     * @param phone  账号
     * @param password  密码
     * @param request
     * @return
     */
    private boolean check(String phone, String password, HttpServletRequest request) {
    	//获取账号对应的 uid
        int uid = userInformationService.selectIdByPhone(phone);    
        System.out.println("uid="+uid);
        
        if (uid == 0 || StringUtils.getInstance().isNullOrEmpty(uid)) {
        	System.out.println("uid check");
            return false;
        }
        
        // 获取 uid 对应的用户对象
        UserInformation userInformation = userInformationService.selectByPrimaryKey(uid);
        if (null == userInformation) {
        	System.out.println("check--userInformation--null");
            return false;
        }
        
        //对输入密码进行 MD5 加密
        password = StringUtils.getInstance().getMD5(password);
        
        //根据用户 uid  查询数据库密码表
        String pwdDB = userPasswordService.selectByUid(userInformation.getId()).getPassword();
        
        //判断密码是否正确
        if (!password.equals(pwdDB)) {
        	System.out.println("pwd check\npassword2="+pwdDB+"\npassword="+password);
            return false;
        }
        
        //密码账号对应正确，将userInformation存储到session中
        request.getSession().setAttribute("userInformation", userInformation);
        request.getSession().setAttribute("uid", uid);
        SaveSession.getInstance().save(phone, System.currentTimeMillis());
        return true;
    }

    /**
     * 获取最详细的分类，第三层
     * @param sort
     * @return
     */
    private Specific selectSpecificBySort(int sort) {
        return specificeService.selectByPrimaryKey(sort);
    }

    /**
     * 获得第二层分类
     * @param cid
     * @return
     */
    private Classification selectClassificationByCid(int cid) {
        return classificationService.selectByPrimaryKey(cid);
    }

    /**
     * 获得第一层分类
     * @param aid
     * @return
     */
    private AllKinds selectAllKindsByAid(int aid) {
        return allKindsService.selectByPrimaryKey(aid);
    }

    public void save(ShopInformation shopInformation, UserRelease userRelease) {
        shopInformationService.insertSelective(shopInformation);
        userReleaseService.insertSelective(userRelease);
    }
}
