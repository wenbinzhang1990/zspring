package com.wenbin.zspring.aop.advice;

import java.lang.reflect.Method;

public class ZBeforeMethodInterceptor extends ZAbstractAdvice implements ZBeforeAdvice,
    ZMethodInterceptor {

  public ZBeforeMethodInterceptor(Method method, Object aspectInstance) {
    super(method, aspectInstance);
  }

  @Override
  public Object invoke(ZMethodInvocation invocation) throws Throwable {
    invokeMethod((ZJoinPoint) invocation, null, null);
    return invocation.process();
  }
}
