package com.wenbin.zspring.demo.aspect;

import com.wenbin.zspring.aop.advice.ZJoinPoint;
import com.wenbin.zspring.aop.annotation.ZAfter;
import com.wenbin.zspring.aop.annotation.ZAround;
import com.wenbin.zspring.aop.annotation.ZAspect;
import com.wenbin.zspring.aop.annotation.ZBefore;
import com.wenbin.zspring.aop.annotation.ZPointcut;
import com.wenbin.zspring.beans.ZComponent;

@ZAspect
@ZComponent
public class SecurityService {
    @ZPointcut("com.wenbin.zspring.demo.service.*.*")
    public void cut()
    {

    }

    @ZBefore("cut")
    public void securityBefore()
    {
        System.out.println("securityBefore");
    }

    @ZAfter("cut")
    public void securityAfter()
    {
        System.out.println("securityAfter");
    }

    @ZAround("cut")
    public void securityAround(ZJoinPoint jp) throws Throwable {
        System.out.println("securityAround before");
        jp.processed();
        System.out.println("securityAround after");
    }
}
