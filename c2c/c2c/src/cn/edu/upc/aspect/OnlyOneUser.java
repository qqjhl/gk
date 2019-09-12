package cn.edu.upc.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class OnlyOneUser {
    @Before(value = "execution(* cn.edu.upc.controller.UserController.login(..))")
    public void isExit(){

    }
}
