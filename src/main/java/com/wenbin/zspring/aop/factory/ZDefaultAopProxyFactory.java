package com.wenbin.zspring.aop.factory;

import com.wenbin.zspring.aop.ZAdviceSupport;
import com.wenbin.zspring.aop.proxy.ZAopProxy;
import com.wenbin.zspring.aop.proxy.ZCglibAopProxy;
import com.wenbin.zspring.aop.proxy.ZJdkDynamicAopProxy;

public class ZDefaultAopProxyFactory {

  public ZAopProxy createAopProxy(ZAdviceSupport adviceSupport) {
    if (adviceSupport.getTargetClass().getInterfaces().length > 0) {
      return new ZJdkDynamicAopProxy(adviceSupport);
    }

    return new ZCglibAopProxy(adviceSupport);
  }
}
