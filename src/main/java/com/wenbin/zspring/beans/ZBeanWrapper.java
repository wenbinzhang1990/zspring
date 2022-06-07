package com.wenbin.zspring.beans;

import com.wenbin.zspring.beans.exception.PopulateBeanException;
import java.lang.reflect.Field;

public class ZBeanWrapper {

  Object wrappedInstance;

  Class<?> clazz;

  public ZBeanWrapper(Object wrappedInstance) {
    this.wrappedInstance = wrappedInstance;
    this.clazz = wrappedInstance.getClass();
  }

  public Object getWrappedInstance() {
    return this.wrappedInstance;
  }

  public void setProperty(Field field, Object bean) {
    field.setAccessible(true);
    try {
      field.set(wrappedInstance, bean);
    } catch (IllegalAccessException e) {
      throw new PopulateBeanException(wrappedInstance.getClass().getName(), field.getName());
    }
  }
}
