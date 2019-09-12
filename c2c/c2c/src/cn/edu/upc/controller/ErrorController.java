package cn.edu.upc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 异常处理
 */
@Controller
public class ErrorController {
    @RequestMapping(value = "/error")
    public String error(){
    	
    	System.out.println("ErrorController--error--error");
    	
        return "error";
    }
}
