package com.wenbin.zspring.aop;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 简化处理，格式如下
 * 类名.方法名，方法名可用*代表全部方法
 * 例： com.wenbin.aop.ZAdvisor com.wenbin.aop.*
 */
public class ZPointCut {

  private String name;

  private String expression;

  public ZPointCut(String name, String expression) {
    this.name = name;
    this.expression = expression;
  }

  public boolean match(Class<?> beanClass) {
    return Pattern.matches(this.expression.substring(0, this.expression.lastIndexOf(".")),
        beanClass.getName());
  }

  public String getMethodName() {
    return this.name;
  }

  public boolean matchMethod(Class<?> targetClass, Method method) {
    String methodName = this.expression.substring(this.expression.lastIndexOf(".") + 1);
    if (methodName.equalsIgnoreCase("*")) {
      return true;
    }

    return methodName.equalsIgnoreCase(method.getName());
  }
}
