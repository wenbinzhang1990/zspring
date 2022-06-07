package com.wenbin.zspring.context;

import com.wenbin.zspring.beans.config.ZBeanDefinition;
import java.util.List;

public interface ZBeanDefinitionReader {

  List<ZBeanDefinition> loadBeanDefinitions(String... locations) throws ClassNotFoundException;
}
