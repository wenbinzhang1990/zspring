package com.wenbin.zspring.aop.advice;

import java.lang.reflect.Method;

public class ZAfterMethodInterceptor extends ZAbstractAdvice implements ZAfterAdvice,
    ZMethodInterceptor {

  public ZAfterMethodInterceptor(Method method, Object aspectInstance) {
    super(method, aspectInstance);
  }

  @Override
  public Object invoke(ZMethodInvocation invocation) throws Throwable {
    Object object = invocation.process();
    invokeMethod((ZJoinPoint) invocation, object, null);
    return object;
  }


}
