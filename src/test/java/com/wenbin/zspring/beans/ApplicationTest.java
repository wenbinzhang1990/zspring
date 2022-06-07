package com.wenbin.zspring.beans;

import com.wenbin.zspring.context.ZAnnotationApplicationContext;
import com.wenbin.zspring.context.ZApplicationContext;
import com.wenbin.zspring.demo.cycle.AClass;
import com.wenbin.zspring.demo.cycle.BClass;
import com.wenbin.zspring.demo.cycle.CClass;
import com.wenbin.zspring.demo.cycle.DClass;
import com.wenbin.zspring.demo.cycle.EClass;
import org.junit.Test;

/**
 *   @Description
 *
 *   @Author wenbin
 */
public class ApplicationTest {

    // 测试ioc初始化
    @Test
    public void testInstance() throws Throwable {
        ZApplicationContext applicationContext = new ZAnnotationApplicationContext("com.wenbin.zspring.demo");
        assert applicationContext.getBeanDefinitionCount() == 9;
        assert applicationContext.getBean("aClass") != null;

        applicationContext = new ZAnnotationApplicationContext("com.wenbin.zspring.demo.cycle");
        assert applicationContext.getBeanDefinitionCount() == 5;
        assert applicationContext.getBean("aClass") != null;

        applicationContext = new ZAnnotationApplicationContext("com.wenbin.zspring.demo.service");
        assert applicationContext.getBeanDefinitionCount() == 2;
    }

    // 测试循环依赖，且对象都是单例
    @Test
    public void testInstanceCycle() throws Throwable {
        ZApplicationContext applicationContext = new ZAnnotationApplicationContext("com.wenbin.zspring.demo");
        AClass aClass = (AClass) applicationContext.getBean("aClass");
        BClass bClass = (BClass) applicationContext.getBean("bClass");
        assert aClass == bClass.aClass;
        assert bClass == aClass.bClass;
    }

    // 测试依赖，且对象都是单例
    @Test
    public void testInstanceDependency() throws Throwable {
        ZApplicationContext applicationContext = new ZAnnotationApplicationContext("com.wenbin.zspring.demo");
        CClass cClass = (CClass) applicationContext.getBean("cClass");
        DClass dClass = (DClass) applicationContext.getBean("dClass");
        EClass eClass = (EClass) applicationContext.getBean("eClass");
        assert cClass.dClass == dClass;
        assert cClass.eClass == eClass;
        assert dClass.eClass == cClass.eClass;
    }

}
