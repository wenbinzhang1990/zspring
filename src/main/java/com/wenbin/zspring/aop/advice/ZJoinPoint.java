package com.wenbin.zspring.aop.advice;

import java.lang.reflect.Method;

public interface ZJoinPoint {

  Object getThis();

  Object[] getArguments();

  Method getMethod();

  void setUserAttribute(String key, Object value);

  Object getUserAttribute(String key);

   Object processed() throws Throwable;
}
