package com.wenbin.zspring.aop.proxy;

import com.wenbin.zspring.aop.ZAdviceSupport;
import com.wenbin.zspring.aop.advice.ZAdvice;
import com.wenbin.zspring.aop.advice.ZCglibMethodInvocation;
import java.lang.reflect.Method;
import java.util.List;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ZCglibAopProxy implements ZAopProxy, MethodInterceptor {

  ZAdviceSupport adviceSupport;

  public ZCglibAopProxy(ZAdviceSupport adviceSupport) {
    super();
    this.adviceSupport = adviceSupport;
  }

  @Override
  public Object getProxy() {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(adviceSupport.getTargetClass());
    enhancer.setCallback(this);
    return enhancer.create();
  }

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
      throws Throwable {
    List<ZAdvice> advices = this.adviceSupport
        .getAdvicesForMethod(method, this.adviceSupport.getTargetClass());
    ZCglibMethodInvocation methodInvocation = new ZCglibMethodInvocation(o, methodProxy,
        adviceSupport.getBean(),
        method, objects, advices);
    return methodInvocation.process();

  }
}
