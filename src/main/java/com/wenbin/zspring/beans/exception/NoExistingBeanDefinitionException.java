package com.wenbin.zspring.beans.exception;

public class NoExistingBeanDefinitionException extends RuntimeException {

  public NoExistingBeanDefinitionException(String beanName) {
    super("no such bean , name:" + beanName);
  }
}
