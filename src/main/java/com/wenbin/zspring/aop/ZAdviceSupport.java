package com.wenbin.zspring.aop;

import com.wenbin.zspring.aop.advice.ZAdvice;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.cglib.core.CollectionUtils;

public class ZAdviceSupport {

    Map<Method, List<ZAdvice>> methodCache = new HashMap<>();

    public Object getBean() {
        return bean;
    }

    private Object bean;

    private List<ZAdvisor> advisors;

    public List<ZAdvisor> getAdvisors() {
        return advisors;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    private Class<?> targetClass;

    public ZAdviceSupport(List<ZAdvisor> advisors, Object bean, Class<?> targetClass) {
        this.advisors = advisors;
        this.targetClass = targetClass;
        this.bean = bean;
    }

    public List<ZAdvice> getAdvicesForMethod(Method method, Class<?> targetClass) {
        List<ZAdvice> result = methodCache.get(method);
        if (result != null && !result.isEmpty()) {
            return result;
        }

        result = new ArrayList<>();
        for (ZAdvisor advisor : advisors) {
            if (advisor.getZPointCut().matchMethod(targetClass, method)) {
                result.add(advisor.getAdvice());
            }
        }

        return result;
    }
}
