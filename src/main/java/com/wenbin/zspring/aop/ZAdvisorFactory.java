package com.wenbin.zspring.aop;

import com.wenbin.zspring.aop.advice.ZAfterMethodInterceptor;
import com.wenbin.zspring.aop.advice.ZAroundMethodInterceptor;
import com.wenbin.zspring.aop.advice.ZBeforeMethodInterceptor;
import com.wenbin.zspring.aop.annotation.ZAfter;
import com.wenbin.zspring.aop.annotation.ZAround;
import com.wenbin.zspring.aop.annotation.ZAspect;
import com.wenbin.zspring.aop.annotation.ZBefore;
import com.wenbin.zspring.aop.annotation.ZPointcut;
import com.wenbin.zspring.beans.config.ZBeanDefinition;
import com.wenbin.zspring.beans.factory.ZDefaultListableBeanFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ZAdvisorFactory {

  private ZDefaultListableBeanFactory beanFactory;

  private List<ZAdvisor> advisors;

  public ZAdvisorFactory(ZDefaultListableBeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  public List<ZAdvisor> getAdvisorForBean(Class<?> beanClass) throws Throwable {
    if (advisors == null) {
      advisors = getAllAdvisor();
    }

    if (advisors.size() == 0) {
      return advisors;
    }

    return getAdvisorThatCanApply(beanClass);
  }

  private List<ZAdvisor> getAdvisorThatCanApply(Class<?> beanClass) {
    List<ZAdvisor> result = new ArrayList<>();
    for (ZAdvisor advisor : advisors) {
      if (advisor.getZPointCut().match(beanClass)) {
        result.add(advisor);
      }
    }

    return result;
  }

  private List<ZAdvisor> getAllAdvisor() throws Throwable {
    List<ZAdvisor> result = new ArrayList<>();
    List<ZBeanDefinition> beanDefinitions = new ArrayList<>(this.beanFactory.getBeanDefinitions());
    for (ZBeanDefinition beanDefinition : beanDefinitions) {
      try {
        Class<?> clazz = Class.forName(beanDefinition.getClassName());
        if (clazz.isAnnotationPresent(ZAspect.class)) {
          result.addAll(buildAdvisors(clazz));
        }
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }

    return result;
  }

  // 仅设置一个pointcut
  private List<ZAdvisor> buildAdvisors(Class<?> clazz) throws Throwable {
    List<ZAdvisor> result = new ArrayList<>();
    Method[] methods = clazz.getMethods();
    ZPointCut zPointCut = null;
    for (Method method : methods) {
      if (method.isAnnotationPresent(ZPointcut.class)) {
        zPointCut = new ZPointCut(method.getName(), method.getAnnotation(ZPointcut.class).value());
        break;
      }
    }

    if (zPointCut == null) {
      return result;
    }

    Object aspectInstance = clazz.getDeclaredConstructor().newInstance();
    for (Method method : methods) {
      if (method.isAnnotationPresent(ZBefore.class) && method.getAnnotation(ZBefore.class).value()
          .equalsIgnoreCase(zPointCut.getMethodName())) {
        result.add(
            new ZAdvisor(method.getName(), new ZBeforeMethodInterceptor(method, aspectInstance), clazz.getName(),
                zPointCut));
      }

      if (method.isAnnotationPresent(ZAfter.class) && method.getAnnotation(ZAfter.class).value()
          .equalsIgnoreCase(zPointCut.getMethodName())) {
        result.add(new ZAdvisor(method.getName(), new ZAfterMethodInterceptor(method, aspectInstance), clazz.getName(),
            zPointCut));
      }

      if (method.isAnnotationPresent(ZAround.class) && method.getAnnotation(ZAround.class).value()
          .equalsIgnoreCase(zPointCut.getMethodName())) {
        result.add(new ZAdvisor(method.getName(), new ZAroundMethodInterceptor(method, aspectInstance), clazz.getName(),
            zPointCut));
      }
    }

    return result;
  }
}
