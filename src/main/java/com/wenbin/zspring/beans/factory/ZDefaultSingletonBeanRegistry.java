package com.wenbin.zspring.beans.factory;

import com.wenbin.zspring.beans.exception.BeanCurrentlyInCreationException;
import com.wenbin.zspring.beans.ZSingletonObjectFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class ZDefaultSingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    private final Map<String, ZSingletonObjectFactory> singletonFactories = new HashMap<>(16);

    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    private final Set<String> singletonsCurrentlyInCreation = new HashSet<>(16);

    public Object getSingleton(String beanName, boolean allowEarlyReference) throws Throwable {
        Object object = singletonObjects.get(beanName);
        if (object == null && singletonsCurrentlyInCreation.contains(beanName)) {
            synchronized (this.singletonObjects) {
                object = earlySingletonObjects.get(beanName);
                if (object == null && allowEarlyReference) {
                    ZSingletonObjectFactory objectFactory = singletonFactories.get(beanName);
                    if (objectFactory != null) {
                        object = objectFactory.getObject();
                        singletonFactories.remove(beanName);
                        earlySingletonObjects.put(beanName, object);
                    }
                }
            }
        }

        return object;
    }

    public void addSingletonObject(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.put(beanName, singletonObject);
            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
        }
    }


    public void addSingleFactory(String beanName, ZSingletonObjectFactory singletonFactory) {
        synchronized (this.singletonObjects) {
            if (!this.singletonObjects.containsKey(beanName)) {
                this.singletonFactories.put(beanName, singletonFactory);
                this.earlySingletonObjects.remove(beanName);
            }
        }
    }

    public void beforeSingletonCreation(String beanName) {
        if (singletonsCurrentlyInCreation.contains(beanName) || !singletonsCurrentlyInCreation
                .add(beanName)) {
            throw new BeanCurrentlyInCreationException(beanName);
        }
    }

    public void afterSingletonCreation(String beanName) {
        if (!singletonsCurrentlyInCreation.contains(beanName) || !singletonsCurrentlyInCreation
                .remove(beanName)) {
            throw new BeanCurrentlyInCreationException(beanName);
        }
    }
}
