package com.wenbin.zspring.aop.advice;

public interface ZMethodInterceptor {
  Object invoke(ZMethodInvocation invocation) throws Throwable;
}
