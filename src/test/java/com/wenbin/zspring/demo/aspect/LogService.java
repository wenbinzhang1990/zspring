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
public class LogService {
  @ZPointcut("com.wenbin.zspring.demo.service.*.*")
  public void cut()
  {

  }

  @ZBefore("cut")
  public void logBefore()
  {
    System.out.println("logBefore");
  }

  @ZAfter("cut")
  public void logAfter()
  {
    System.out.println("logAfter");
  }

  @ZAround("cut")
  public void logAround(ZJoinPoint jp) throws Throwable {
    System.out.println("logAround before");
    jp.processed();
    System.out.println("logAround after");
  }
}
