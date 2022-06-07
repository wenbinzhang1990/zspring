package com.wenbin.zspring.beans.exception;

import com.wenbin.zspring.beans.config.ZBeanDefinition;

public class ExistingBeanDefinitionException extends RuntimeException {

  public ExistingBeanDefinitionException(ZBeanDefinition beanDefinition) {
    super("existing same bean definition,bean name:" + beanDefinition.getBeanName());
  }
}
