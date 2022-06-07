package com.wenbin.zspring.aop;

import com.wenbin.zspring.aop.factory.ZDefaultAopProxyFactory;
import com.wenbin.zspring.aop.proxy.ZAopProxy;
import com.wenbin.zspring.beans.factory.ZBeanPostProcessor;
import com.wenbin.zspring.beans.factory.ZDefaultListableBeanFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZAopProxyCreator implements ZBeanPostProcessor {

    private Map<String, Object> map = new HashMap<>();
    ZDefaultListableBeanFactory beanFactory;
    ZAdvisorFactory advisorFactory;
    ZDefaultAopProxyFactory aopProxyFactory;

    public ZAopProxyCreator(ZDefaultListableBeanFactory defaultListableBeanFactory) {
        this.beanFactory = defaultListableBeanFactory;
        advisorFactory = new ZAdvisorFactory(defaultListableBeanFactory);
        aopProxyFactory = new ZDefaultAopProxyFactory();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
        Object object = bean;
        if (!map.containsKey(beanName)) {
            object = wrapIfNecessary(bean);
            map.put(beanName, object);
        }
        return object;
    }

    public Object getEarlyReference(String beanName, Object bean) throws Throwable {
        map.put(beanName, bean);
        return wrapIfNecessary(bean);
    }

    private Object wrapIfNecessary(Object bean) throws Throwable {
        List<ZAdvisor> advisors = advisorFactory.getAdvisorForBean(bean.getClass());
        if (advisors.isEmpty()) {
            return bean;
        }

        ZAdviceSupport adviceSupport = new ZAdviceSupport(advisors, bean, bean.getClass());
        ZAopProxy aopProxy = aopProxyFactory.createAopProxy(adviceSupport);
        return aopProxy.getProxy();
    }
}
