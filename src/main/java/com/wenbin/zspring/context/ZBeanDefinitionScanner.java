package com.wenbin.zspring.context;

import java.util.List;

public interface ZBeanDefinitionScanner {

  List<String> scan(String... scanPaths);
}
