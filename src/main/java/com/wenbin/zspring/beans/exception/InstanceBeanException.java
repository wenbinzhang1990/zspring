package com.wenbin.zspring.beans.exception;

public class InstanceBeanException extends RuntimeException {

  public InstanceBeanException(String beanName) {
    super("instance failed , name:" + beanName);
  }
}
