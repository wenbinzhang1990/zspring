package com.wenbin.zspring.aop.advice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZJdkMethodInvocation implements ZMethodInvocation, ZJoinPoint {

  private Object proxy;
  private Object bean;
  private Method method;
  private Object[] args;
  private List<ZAdvice> advices;
  private int index = -1;

  private Map<String, Object> map = new HashMap<>();

  public ZJdkMethodInvocation(Object proxy, Object bean, Method method, Object[] args,
      List<ZAdvice> advices) {
    this.proxy = proxy;
    this.bean = bean;
    this.method = method;
    this.args = args;
    this.advices = advices;
  }

  @Override
  public Object process() throws Throwable {
    if (index == this.advices.size() - 1) {
      return method.invoke(bean, args);
    }

    ZAdvice advice = this.advices.get(++index);
    if (advice instanceof ZMethodInterceptor) {
      return ((ZMethodInterceptor) advice).invoke(this);
    }

    return process();
  }

  @Override
  public Object getThis() {
    return this;
  }

  @Override
  public Object[] getArguments() {
    return this.args;
  }

  @Override
  public Method getMethod() {
    return this.method;
  }

  @Override
  public void setUserAttribute(String key, Object value) {
    map.put(key, value);
  }

  @Override
  public Object getUserAttribute(String key) {
    return map.get(key);
  }

  @Override
  public Object processed() throws Throwable {
    return this.process();
  }
}
