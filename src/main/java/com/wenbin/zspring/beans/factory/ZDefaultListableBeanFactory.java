package com.wenbin.zspring.beans.factory;

import com.wenbin.zspring.beans.exception.ExistingBeanDefinitionException;
import com.wenbin.zspring.beans.ZAutowired;
import com.wenbin.zspring.beans.ZBeanFactory;
import com.wenbin.zspring.beans.ZBeanWrapper;
import com.wenbin.zspring.beans.ZSingletonObjectFactory;
import com.wenbin.zspring.beans.config.ZBeanDefinition;
import com.wenbin.zspring.beans.exception.InstanceBeanException;
import com.wenbin.zspring.beans.exception.NoExistingBeanDefinitionException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ZDefaultListableBeanFactory extends ZDefaultSingletonBeanRegistry implements
        ZBeanFactory {

    private Map<String, ZBeanDefinition> beanDefinitionMap = new HashMap<>();

    private List<String> beanDefinitionsNames = new ArrayList<>();

    private final List<ZBeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

    @Override
    public Object getBean(String beanName) throws Throwable {
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new NoExistingBeanDefinitionException(beanName);
        }

        Object instance = getSingleton(beanName, true);
        if (instance == null) {
            instance = createBean(beanName);
        }

        return instance;
    }

    private Object createBean(String beanName) throws Throwable {
        beforeSingletonCreation(beanName);

        // get bean definition
        ZBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);

        // create bean instance
        ZBeanWrapper beanWrapper = createBeanInstance(beanName, beanDefinition);

        Object object = beanWrapper.getWrappedInstance();

        // add single factory
        addSingleFactory(beanName, object);

        // populate bean
        populateBean(beanWrapper);

        // initialize bean
        Object exposedObject = initializeBean(beanName, beanWrapper.getWrappedInstance());

        // 循环依赖中的bean会被替换代理，这里就是把已经被代理的bean返回，而不是返回原生bean
        Object earlyObject = getSingleton(beanName, false);
        if (exposedObject == object && earlyObject != null) {
            exposedObject = earlyObject;
        }

        afterSingletonCreation(beanName);

        // add singleton object
        addSingletonObject(beanName, exposedObject);

        return exposedObject;
    }


    private Object initializeBean(String beanName, Object wrappedInstance) throws Throwable {
        Object object = wrappedInstance;
        for (ZBeanPostProcessor beanPostProcessor : this.beanPostProcessors) {
            object = beanPostProcessor.postProcessBeforeInitialization(wrappedInstance, beanName);
        }

        for (ZBeanPostProcessor beanPostProcessor : this.beanPostProcessors) {
            object = beanPostProcessor.postProcessAfterInitialization(wrappedInstance, beanName);
        }

        return object;
    }

    private void addSingleFactory(String beanName, Object object) {
        ZSingletonObjectFactory singletonObjectFactory = new ZSingletonObjectFactory(beanName, object,
                this);
        addSingleFactory(beanName, singletonObjectFactory);
    }

    private void populateBean(ZBeanWrapper beanWrapper) throws Throwable {
        List<Field> fields = getAllAutoWiredField(beanWrapper.getWrappedInstance().getClass());
        for (Field field : fields) {
            String autowiredBeanName = field.getName();
            beanWrapper.setProperty(field, getBean(autowiredBeanName));
        }
    }

    private List<Field> getAllAutoWiredField(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            Field[] fieldArray = clazz.getDeclaredFields();
            for (Field field : fieldArray) {
                if (field.isAnnotationPresent(ZAutowired.class)) {
                    fields.add(field);
                }
            }

            clazz = clazz.getSuperclass();
        }

        return fields;
    }

    private ZBeanWrapper createBeanInstance(String beanName, ZBeanDefinition beanDefinition) {
        Object object;
        try {
            Class<?> clazz = Class.forName(beanDefinition.getClassName());
            object = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception exception) {
            throw new InstanceBeanException(beanName);
        }

        return new ZBeanWrapper(object);
    }


    public void registerBeanDefinitions(List<ZBeanDefinition> beanDefinitions) {
        for (ZBeanDefinition beanDefinition : beanDefinitions) {
            registerBeanDefinition(beanDefinition.getBeanName(), beanDefinition);
        }
    }

    public void registerBeanDefinition(String beanName, ZBeanDefinition beanDefinition) {
        if (this.beanDefinitionMap.containsKey(beanName)) {
            throw new ExistingBeanDefinitionException(beanDefinition);
        }

        this.beanDefinitionMap.put(beanName, beanDefinition);
        this.beanDefinitionsNames.add(beanName);
    }

    public void addBeanPostProcessor(ZBeanPostProcessor ZBeanPostProcessor) {
        this.beanPostProcessors.remove(ZBeanPostProcessor);
        this.beanPostProcessors.add(ZBeanPostProcessor);
    }

    public void finishInitialization() throws Throwable {
        for (String beanName : beanDefinitionsNames) {
            getBean(beanName);
        }
    }

    public Collection<ZBeanDefinition> getBeanDefinitions() {
        return this.beanDefinitionMap.values();
    }

    public List<ZBeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }
}
