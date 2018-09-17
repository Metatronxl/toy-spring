package com.titizz.simulation.toyspring.simple;

import java.lang.reflect.Method;

/**
 * Created by code4wt on 17/8/19.
 */
public class BeforeAdvice implements Advice {

    private Object bean;

    private MethodInvocation methodInvocation;

    private MethodInvocation afterInvovation;

    public BeforeAdvice(Object bean, MethodInvocation methodInvocation,MethodInvocation afterInvovation) {
        this.bean = bean;
        this.methodInvocation = methodInvocation;
        this.afterInvovation = afterInvovation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodInvocation.invoke();
        method.invoke(bean, args);
        afterInvovation.invoke();
        return null;
    }
}
