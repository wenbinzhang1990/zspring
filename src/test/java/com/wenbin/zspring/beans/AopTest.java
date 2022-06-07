package com.wenbin.zspring.beans;

import com.wenbin.zspring.context.ZAnnotationApplicationContext;
import com.wenbin.zspring.context.ZApplicationContext;
import com.wenbin.zspring.demo.service.Action;
import com.wenbin.zspring.demo.service.CglibActionService;
import com.wenbin.zspring.demo.service.JdkActionService;
import com.wenbin.zspring.demo.service.LogAction;
import java.lang.reflect.Proxy;
import org.junit.Test;

/**
 *   @Description
 *
 *   @Author wenbin
 */
public class AopTest {

    public static void main(String[] args) {
        JdkActionService jdkActionService = new JdkActionService();
        jdkActionService.action();

        assert jdkActionService instanceof JdkActionService;
    }

    @Test
    public void jdkDynamicAopTest() throws Throwable {
        ZApplicationContext applicationContext = new ZAnnotationApplicationContext("com.wenbin.zspring.demo");

        Object object = applicationContext.getBean("jdkActionService");
        assert !(object instanceof JdkActionService);
        assert object instanceof Proxy;
        assert object instanceof Action;
        ((Action) object).action();
        System.out.println("===============");
        assert object instanceof LogAction;
        ((LogAction) object).actionAspectByLog();
    }

    @Test
    public void cglibAopTest() throws Throwable {
        ZApplicationContext applicationContext = new ZAnnotationApplicationContext("com.wenbin.zspring.demo");

        Object object = applicationContext.getBean("cglibActionService");
        assert object instanceof CglibActionService;
        ((CglibActionService) object).actionAspectByLog();
        System.out.println("===============");
        ((CglibActionService) object).action();
        System.out.println("===============");
        ((CglibActionService) object).actionAspectByLogAndSecurity();
    }
}
