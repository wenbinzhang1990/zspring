package com.wenbin.zspring.beans.factory;

public interface ZBeanPostProcessor {

  default Object postProcessBeforeInitialization(Object bean, String beanName) {
    return bean;
  }

  default Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
    return bean;
  }
}
