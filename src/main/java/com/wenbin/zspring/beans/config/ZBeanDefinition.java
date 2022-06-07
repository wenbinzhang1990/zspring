package com.wenbin.zspring.beans.config;

public class ZBeanDefinition {

  public ZBeanDefinition(String beanName, String className) {
    this.beanName = beanName;
    this.className = className;
  }

  public String getBeanName() {
    return beanName;
  }


  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  private String beanName;

  private String className;


}

