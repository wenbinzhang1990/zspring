package com.wenbin.zspring.aop.advice;

import java.lang.reflect.Method;

public abstract class ZAbstractAdvice {

  private Object aspectInstance;
  private Method method;

  public ZAbstractAdvice(Method method, Object aspectInstance) {
    this.aspectInstance = aspectInstance;
    this.method = method;
  }


  protected Object invokeMethod(ZJoinPoint jp, Object retVal, Throwable t)
      throws Throwable {

    Class<?>[] paramTypes = method.getParameterTypes();
    if (paramTypes.length == 0) {
      return method.invoke(aspectInstance);
    } else {
      Object[] args = new Object[paramTypes.length];
      for (int i = 0; i < paramTypes.length; i++) {
        if (paramTypes[i] == ZJoinPoint.class) {
          args[i] = jp;
        } else if (paramTypes[i] == Throwable.class) {
          args[i] = t;
        } else if (paramTypes[i] == Object.class) {
          args[i] = retVal;
        }
      }
      return this.method.invoke(aspectInstance, args);
    }
  }
}
