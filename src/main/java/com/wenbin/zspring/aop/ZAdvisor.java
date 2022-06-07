package com.wenbin.zspring.aop;

import com.wenbin.zspring.aop.advice.ZAdvice;

public class ZAdvisor {

    private String methodName;

    private ZPointCut ZPointCut;

    private ZAdvice advice;

    private String aspectClassName;

    public ZAdvisor(String methodName, ZAdvice advice, String aspectClassName,
            ZPointCut zPointCut) {
        this.methodName = methodName;
        this.ZPointCut = zPointCut;
        this.advice = advice;
        this.aspectClassName = aspectClassName;
    }


    public ZPointCut getZPointCut() {
        return this.ZPointCut;
    }

    public ZAdvice getAdvice() {
        return this.advice;
    }
}
