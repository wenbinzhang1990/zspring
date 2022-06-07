package com.wenbin.zspring.beans;

import com.wenbin.zspring.aop.ZAopProxyCreator;
import com.wenbin.zspring.beans.factory.ZBeanPostProcessor;
import com.wenbin.zspring.beans.factory.ZDefaultListableBeanFactory;

public class ZSingletonObjectFactory {

  private final String beanName;
  private final Object object;
  ZDefaultListableBeanFactory beanFactory;

  public ZSingletonObjectFactory(String beanName, Object object,
      ZDefaultListableBeanFactory beanFactory) {
    this.beanName = beanName;
    this.object = object;
    this.beanFactory = beanFactory;
  }

  public Object getObject() throws Throwable {
    for (ZBeanPostProcessor beanPostProcessor : beanFactory.getBeanPostProcessors()) {
      if (beanPostProcessor instanceof ZAopProxyCreator) {
        return ((ZAopProxyCreator) beanPostProcessor).getEarlyReference(beanName, object);
      }
    }

    return object;
  }
}
