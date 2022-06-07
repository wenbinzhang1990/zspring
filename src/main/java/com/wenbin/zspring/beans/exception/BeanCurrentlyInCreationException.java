package com.wenbin.zspring.beans.exception;

public class BeanCurrentlyInCreationException extends RuntimeException {

  public BeanCurrentlyInCreationException(String beanName) {
    super(beanName + " bean is currently in creation");
  }
}
