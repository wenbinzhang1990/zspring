package com.wenbin.zspring.context;

import com.wenbin.zspring.beans.ZBeanFactory;

public interface ZApplicationContext extends ZBeanFactory {

    int getBeanDefinitionCount();
}
