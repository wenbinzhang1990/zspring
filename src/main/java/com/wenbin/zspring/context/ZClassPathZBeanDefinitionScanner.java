package com.wenbin.zspring.context;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ZClassPathZBeanDefinitionScanner implements ZBeanDefinitionScanner {
  private List<String> classNames = new ArrayList<>();

  @Override
  public List<String> scan(String... scanPackages) {
    for (String l : scanPackages) {
      String scanPath = l.replaceAll("\\.", "/");
      URL url = this.getClass().getClassLoader().getResource(scanPath);
      File file = new File(url.getFile());
      getClassNames(file, l);
    }

    return classNames;
  }

  private void getClassNames(File file, String scanPath) {
    for (File f : file.listFiles()) {
      if (f.isDirectory()) {
        getClassNames(f, scanPath+"."+f.getName());
      } else {
        classNames.add(scanPath + "." + f.getName().replace(".class", ""));
      }
    }
  }
}
