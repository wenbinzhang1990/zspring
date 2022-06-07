package com.wenbin.zspring.beans.exception;

public class PopulateBeanException extends RuntimeException {

  public PopulateBeanException(String beanName, String fieldName) {
    super("populate bean failed,bean name:" + beanName + ";field name:" + fieldName);
  }
}
