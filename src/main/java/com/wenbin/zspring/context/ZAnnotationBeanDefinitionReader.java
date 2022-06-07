package com.wenbin.zspring.context;

import com.wenbin.zspring.beans.BeanNameGenerator;
import com.wenbin.zspring.beans.ZComponent;
import com.wenbin.zspring.beans.config.ZBeanDefinition;
import java.util.ArrayList;
import java.util.List;

public class ZAnnotationBeanDefinitionReader implements ZBeanDefinitionReader {

    private ZBeanDefinitionScanner scanner;


    public ZAnnotationBeanDefinitionReader() {
        this.scanner = new ZClassPathZBeanDefinitionScanner();
    }

    @Override
    public List<ZBeanDefinition> loadBeanDefinitions(String... scanPaths)
            throws ClassNotFoundException {
        List<String> classNames = this.scanner.scan(scanPaths);
        return doCreateBeanDefinition(classNames);
    }

    private List<ZBeanDefinition> doCreateBeanDefinition(List<String> classNames)
            throws ClassNotFoundException {
        List<ZBeanDefinition> beanDefinitions = new ArrayList<>();
        for (String className : classNames) {
            Class<?> clazz = Class.forName(className);
            if (clazz.isInterface()) {
                continue;
            }

            //别名先不做了
            if (!clazz.isAnnotationPresent(ZComponent.class)) {
                continue;
            }

            beanDefinitions.add(new ZBeanDefinition(BeanNameGenerator.generateBeanName(clazz.getSimpleName()), className));
        }

        return beanDefinitions;
    }
}
