package com.wenbin.zspring.context;

import com.wenbin.zspring.aop.ZAopProxyCreator;
import com.wenbin.zspring.beans.factory.ZDefaultListableBeanFactory;
import com.wenbin.zspring.beans.config.ZBeanDefinition;
import java.util.List;

public class ZAnnotationApplicationContext implements ZApplicationContext {

    private ZBeanDefinitionReader reader;

    private ZDefaultListableBeanFactory defaultListableBeanFactory;

    private ZAnnotationApplicationContext() {
        this.reader = new ZAnnotationBeanDefinitionReader();
        this.defaultListableBeanFactory = new ZDefaultListableBeanFactory();
    }


    public ZAnnotationApplicationContext(String... scanPaths) throws Throwable {
        this();
        scan(scanPaths);
        refresh();
    }

    private void refresh() throws Throwable {
        // 1.register bean postprocessor
        registerBeanPostProcessor();

        // 2.instantiate non-lazy beans
        doInstance();
    }

    private void registerBeanPostProcessor() {
        this.defaultListableBeanFactory.addBeanPostProcessor(new ZAopProxyCreator(this.defaultListableBeanFactory));
    }


    public void scan(String... scanPaths) throws Throwable {
        if (scanPaths == null || scanPaths.length == 0) {
            throw new IllegalArgumentException();
        }

        // 1.load bean definitions
        List<ZBeanDefinition> beanDefinitions = this.reader.loadBeanDefinitions(scanPaths);

        // 2.register bean definitions
        this.defaultListableBeanFactory.registerBeanDefinitions(beanDefinitions);


    }


    private void doInstance() throws Throwable {
        this.defaultListableBeanFactory.finishInitialization();
    }


    @Override
    public Object getBean(String beanName) throws Throwable {
        return this.defaultListableBeanFactory.getBean(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.defaultListableBeanFactory.getBeanDefinitions().size();
    }
}
