package com.wenbin.zspring.aop.proxy;

import com.wenbin.zspring.aop.ZAdviceSupport;
import com.wenbin.zspring.aop.advice.ZAdvice;
import com.wenbin.zspring.aop.advice.ZJdkMethodInvocation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class ZJdkDynamicAopProxy implements ZAopProxy, InvocationHandler {

  ZAdviceSupport adviceSupport;

  public ZJdkDynamicAopProxy(ZAdviceSupport adviceSupport) {
    super();
    this.adviceSupport = adviceSupport;
  }

  @Override
  public Object getProxy() {
    return Proxy.newProxyInstance(this.getClass().getClassLoader(),
        adviceSupport.getTargetClass().getInterfaces(), this);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    List<ZAdvice> advices = this.adviceSupport
        .getAdvicesForMethod(method, this.adviceSupport.getTargetClass());
    ZJdkMethodInvocation methodInvocation = new ZJdkMethodInvocation(proxy, adviceSupport.getBean(),
        method, args, advices);
    return methodInvocation.process();
  }
}
