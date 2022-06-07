package com.wenbin.zspring.aop.advice;

import java.lang.reflect.Method;

public class ZAroundMethodInterceptor extends ZAbstractAdvice implements ZAroundAdvice,
    ZMethodInterceptor {

  public ZAroundMethodInterceptor(Method method, Object aspectInstance) {
    super(method, aspectInstance);
  }

  @Override
  public Object invoke(ZMethodInvocation invocation) throws Throwable {
    return this.invokeMethod((ZJoinPoint) invocation,null,null);
  }
}
