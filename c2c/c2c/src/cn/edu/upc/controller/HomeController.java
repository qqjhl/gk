package cn.edu.upc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.upc.bean.ShopInformationBean;
import cn.edu.upc.pojo.AllKinds;
import cn.edu.upc.pojo.Classification;
import cn.edu.upc.pojo.ShopContext;
import cn.edu.upc.pojo.ShopInformation;
import cn.edu.upc.pojo.Specific;
import cn.edu.upc.pojo.UserInformation;
import cn.edu.upc.service.AllKindsService;
import cn.edu.upc.service.ClassificationService;
import cn.edu.upc.service.ShopContextService;
import cn.edu.upc.service.ShopInformationService;
import cn.edu.upc.service.SpecificeService;
import cn.edu.upc.tool.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页面控制器
 */
@Controller
public class HomeController {
    @Resource
    private ShopInformationService shopInformationService;
    @Resource
    private SpecificeService specificeService;
    @Resource
    private ClassificationService classificationService;
    @Resource
    private AllKindsService allKindsService;
    @Resource
    private ShopContextService shopContextService;


    @RequestMapping(value = {"/","/home.do"})
    public String home(HttpServletRequest request, Model model) {
    	System.out.println("welcome home !");
    	System.out.println("HomeController--home--home.do");
    	
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        
        //如果用户登录，session会有一个userInformation
        if (!StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            model.addAttribute("userInformation", userInformation);
        } else {
            userInformation = new UserInformation();
            model.addAttribute("userInformation", userInformation);
        }
        //一般形式进入首页
        try {
            List<ShopInformation> shopInformations = selectTen(1,5);
            
            List<ShopInformationBean> list = new ArrayList<>();
            int counts = getShopCounts();
            model.addAttribute("shopInformationCounts", counts);
            String stringBuffer;

            for (ShopInformation shopInformation : shopInformations) {
                stringBuffer = getSortName(shopInformation.getSort());
                ShopInformationBean shopInformationBean = new ShopInformationBean();
                
                shopInformationBean.setId(shopInformation.getId());
                shopInformationBean.setName(shopInformation.getName());
                shopInformationBean.setLevel(shopInformation.getLevel());
                shopInformationBean.setPrice(shopInformation.getPrice().doubleValue());
                
                if(shopInformation.getLevel() >= 4) {
//                	shopInformationBean.setRemark("喜欢就加购呀");
                }else {
                	shopInformationBean.setRemark("  ");
                }
                
                shopInformationBean.setSort(stringBuffer);
                shopInformationBean.setQuantity(shopInformation.getQuantity());
                shopInformationBean.setTransaction(shopInformation.getTransaction());
                shopInformationBean.setUid(shopInformation.getUid());
                shopInformationBean.setImage(shopInformation.getImage());
                
                list.add(shopInformationBean);
            }
            model.addAttribute("shopInformationBean", list);
        } catch (Exception e) {
            e.printStackTrace();
//            return "page/login";
            return "page/login_page";
        }

        return "index";
    }
    
    /**
     * 进入商城
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/mall_page.do")
    public String mallPage(HttpServletRequest request, Model model) {
    	
    	System.out.println("HomeController--mallPage--mall_page.do");
    	
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            userInformation = new UserInformation();
            model.addAttribute("userInformation", userInformation);
        } else {
            model.addAttribute("userInformation", userInformation);
        }
        try {
            List<ShopInformation> shopInformations = selectTen(1,12);
            List<ShopInformationBean> list = new ArrayList<>();
            int counts = getShopCounts();
            model.addAttribute("shopInformationCounts", counts);
            String sortName;
            for (ShopInformation shopInformation : shopInformations) {
                int sort = shopInformation.getSort();
                sortName = getSortName(sort);
                ShopInformationBean shopInformationBean = new ShopInformationBean();
                shopInformationBean.setId(shopInformation.getId());
                shopInformationBean.setName(shopInformation.getName());
                shopInformationBean.setLevel(shopInformation.getLevel());
                shopInformationBean.setRemark(shopInformation.getRemark());
                shopInformationBean.setPrice(shopInformation.getPrice().doubleValue());
                shopInformationBean.setSort(sortName);
                shopInformationBean.setQuantity(shopInformation.getQuantity());
                shopInformationBean.setTransaction(shopInformation.getTransaction());
                shopInformationBean.setUid(shopInformation.getUid());
                shopInformationBean.setImage(shopInformation.getImage());
                list.add(shopInformationBean);
            }
            model.addAttribute("shopInformationBean", list);
        } catch (Exception e) {
            e.printStackTrace();
            return "page/login_page";
        }
        return "page/mall_page";
    }

    /*
     * 通过分类的第三层id获取全名
     */
    private String getSortName(int sort){
        StringBuilder stringBuffer = new StringBuilder();
        Specific specific = selectSpecificBySort(sort);
        int cid = specific.getCid();
        Classification classification = selectClassificationByCid(cid);
        int aid = classification.getAid();
        AllKinds allKinds = selectAllKindsByAid(aid);
        stringBuffer.append(allKinds.getName());
        stringBuffer.append("-");
        stringBuffer.append(classification.getName());
        stringBuffer.append("-");
        stringBuffer.append(specific.getName());
        return stringBuffer.toString();
    }

    /**
     * 获得分类中的第一层
     * @return
     */
    @RequestMapping(value = "/getAllKinds.do")
    @ResponseBody
    public List<AllKinds> getAllKind(){
        return getAllKinds();
    }

    /**
     * 获得分类中的第二层，通过第一层的id
     * @param id
     * @return
     */
    @RequestMapping(value = "/getClassification.do",method = RequestMethod.POST)
    @ResponseBody
    public List<Classification> getClassificationByAid(@RequestParam int id){
        return selectAllClassification(id);
    }
    
    /**
     * 通过第二层的id获取对应的第三层
     * @param id
     * @return
     */
    @RequestMapping(value = "/getSpecific.do")
    @ResponseBody
    public List<Specific> getSpecificByCid(@RequestParam int id) {
        return selectAllSpecific(id);
    }

    /**
     * get the shops counts
     * @return
     */
    @RequestMapping(value = "/getShopsCounts.do")
    @ResponseBody
    public Map<String, Integer> getShopsCounts(){
        Map<String, Integer> map = new HashMap<>();
        int counts = 0;
        try {
            counts = shopInformationService.getCounts();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("counts", counts);
            return map;
        }
        map.put("counts", counts);
        return map;
    }
    
    /**
     * 
     * @param start
     * @return
     */
    @RequestMapping(value = "/getShops.do")
    @ResponseBody
    public List<ShopInformation> getShops(@RequestParam int start){
        List<ShopInformation> list = new ArrayList<>();
        try {
            int end = 12;
            list = selectTen(start,end);
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }



    /**
     * 获取商品，分页,一次性获取end个
     * @param start
     * @param end
     * @return
     */
    private List<ShopInformation> selectTen(int start,int end) {
        Map<String, Integer> map = new HashMap<>();
        map.put("start",(start-1)*end);
        map.put("end", end);
        List<ShopInformation> list = shopInformationService.selectTen(map);
        return list;
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

    /**
     * 获得第一层所有
     * @return
     */
    private List<AllKinds> getAllKinds(){
        return allKindsService.selectAll();
    }

    /**
     * 根据第一层的id获取该层下的第二层
     * @param aid
     * @return
     */
    private List<Classification> selectAllClassification(int aid){
        return classificationService.selectByAid(aid);
    }
    
    /**
     * 根据第二层的id获取其对应的第三层所有id
     * @param cid
     * @return
     */
    private List<Specific> selectAllSpecific(int cid){
        return specificeService.selectByCid(cid);
    }

    /**
     * 获得商品总页数
     * @return
     */
    private int getShopCounts(){
        return shopInformationService.getCounts();
    }

    /**
     * 获得商品留言总页数
     * @param sid
     * @return
     */
    @SuppressWarnings("unused")
	private int getShopContextCounts(int sid) {
        return shopContextService.getCounts(sid);
    }

    /**
     * 获得商品留言，10条
     * @param sid
     * @param start
     * @return
     */
    @SuppressWarnings("unused")
	private List<ShopContext> selectShopContextBySid(int sid, int start) {
        return shopContextService.findById(sid, (start-1)*10);
    }
}
