package cn.edu.upc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import cn.edu.upc.pojo.Consumption;
import cn.edu.upc.pojo.GoodsCar;
import cn.edu.upc.pojo.ShopInformation;
import cn.edu.upc.pojo.UserInformation;
import cn.edu.upc.service.ConsumptionService;
import cn.edu.upc.service.GoodsCarService;
import cn.edu.upc.service.ShopInformationService;
import cn.edu.upc.tool.AlipayUtils;
import cn.edu.upc.tool.SampleTime;
import cn.edu.upc.tool.StringUtils;

/**
 * 支付宝支付控制类
 * @author JingHongLi
 *
 */
@Controller
public class AlipayController {
	
	@Resource
	private GoodsCarService goodsCarService;
	@Resource
	private ShopInformationService shopInformationService;
	@Resource
	private ConsumptionService consumptionService;

	@RequestMapping(value="/pay.do")
	public String payIndex(HttpServletRequest request, Model model) {
		
		System.out.println("AlipayController--payIndex--pay.do");
		
		UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
		StringBuilder trade_name = new StringBuilder();
		
		String trade_money = request.getParameter("number");
		
		if(StringUtils.getInstance().isNullOrEmpty(trade_money)) {
			return "error";
		}
		
		String[] p_li = trade_money.split("n");
		
		if(p_li.length != 2) {
			return "error";
		}
		
		String pri = p_li[0];
		StringBuilder c = new StringBuilder();
	    for(int i=0;i<pri.length();i++){        
	        c.append((char)(pri.charAt(i)-i-1));      
	    }  
	    
	    String pri2 = p_li[1];
		StringBuilder c2 = new StringBuilder();
	    for(int i=0;i<pri2.length();i++){        
	        c2.append((char)(pri2.charAt(i)-i-2));      
	    }  
		
		if(!c.toString().equals(c2.toString())) {
			return "error";
		}

		List<GoodsCar> goodsCars = goodsCarService.selectByUid(userInformation.getId());
		
		for(int i = 0; i < goodsCars.size(); i++) {
			
			ShopInformation shopInformation = shopInformationService.selectByPrimaryKey(goodsCars.get(i).getSid());
			if(!StringUtils.getInstance().isNullOrEmpty(shopInformation)) {
				trade_name.append(shopInformation.getName()+"^");
			}
		}
		
		Random ra =new Random();
		model.addAttribute("trade_number", (int)(ra.nextDouble()*999999999));
		model.addAttribute("trade_name", trade_name.toString());
		model.addAttribute("trade_money", c);
		model.addAttribute("trade_desc", "交易很满意");
		return "alipay_index";
	}
	
	@RequestMapping(value="/pagePay.do", method=RequestMethod.POST)
	public void pagePay(HttpServletRequest request, Model model, HttpServletResponse httpResponse) throws Exception, Exception {
		
		System.out.println("AlipayController--pagePay--pagePay.do");
		
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayUtils.gatewayUrl, AlipayUtils.app_id, AlipayUtils.merchant_private_key, "json", AlipayUtils.charset, AlipayUtils.alipay_public_key, AlipayUtils.sign_type);
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayUtils.return_url);
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no;
		try {
			out_trade_no = new String(request.getParameter("WIDout_trade_no"));
			//付款金额，必填
			String total_amount = new String(request.getParameter("WIDtotal_amount"));
			//订单名称，必填
			String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
			//商品描述，可空
			String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
			
			System.out.println("body==="+body);
			
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
					+ "\"total_amount\":\""+ total_amount +"\"," 
					+ "\"subject\":\""+ subject +"\"," 
					+ "\"body\":\""+ body +"\"," 
					+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
						
			//生成HTML返回给页面
			String form = alipayClient.pageExecute(alipayRequest).getBody();
			
			httpResponse.setContentType("text/html;charset=utf-8");
			httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			httpResponse.sendRedirect("error");
//			request.getRequestDispatcher("xxx").forward(request, httpResponse);
		}
	}
	
	@RequestMapping(value="/returnUrl.do")
	public void returnUrl(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
		
		System.out.println("AlipayController--returnUrl--returnUrl.do");
		
		UserInformation currentInfo = (UserInformation) request.getSession().getAttribute("userInformation"); 
		
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用 
			valueStr = new String(valueStr);
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayUtils.alipay_public_key, AlipayUtils.charset, AlipayUtils.sign_type); //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no"));
		
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no"));
		
			//付款金额
			String total_amount = new String(request.getParameter("total_amount"));
			
			//订单名
			//String subject = new String(request.getParameter("subject"));
			
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("交易成功，本次交易的详细信息如下:");
			response.getWriter().write("<br/>支付宝交易号:"+trade_no+"<br/>商户订单号:"+out_trade_no+"<br/>付款金额:"+total_amount+"RMB<br/><br/>");
			response.getWriter().write("<a href=\"/c2c/\" style=\"text-decoration: none;color: #fd1313\">返回首页</a>");
			response.getWriter().flush();
			response.getWriter().close();
			
			List<GoodsCar> goodsList = goodsCarService.selectByUid(currentInfo.getId());
			
			for(int i = 0; i < goodsList.size(); i++) {
				ShopInformation shop = shopInformationService.selectByPrimaryKey(goodsList.get(i).getSid());
				
				Consumption consumption = new Consumption();
				consumption.setBuyer(currentInfo.getId());
				consumption.setSeller(shop.getUid());
				consumption.setSid(shop.getId());
				consumption.setCounts(1);
				consumption.setModified(SampleTime.getTime(new Date()));
				
				consumptionService.insert(consumption);
				

				//商品不可见
				shop.setDisplay(0);
				shopInformationService.updateByPrimaryKeySelective(shop);
				System.out.println("商品下架id="+shop.getId());
				
				//删除购物车记录
				int deRes = goodsCarService.deleteByPrimaryKey(goodsList.get(i).getId());
				System.out.println("删除用户的购物车商品id="+goodsList.get(i).getId());
				
				//删除异常
				if(deRes != 1) {
					
				}
				
			}
			
			List<GoodsCar> test = goodsCarService.selectByUid(currentInfo.getId());
			System.out.println("test="+test.size());
			
			System.out.println("交易成功，本次交易的详细信息如下:");
			System.out.println("<br/>支付宝交易号:"+trade_no+"<br/>商户订单号:"+out_trade_no+"<br/>付款金额:"+total_amount+"RMB");
		}else {
			System.out.println("验签失败");
			response.getWriter().write("验签失败");
		}
		
	}
	
}
