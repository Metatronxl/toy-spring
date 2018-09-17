package com.titizz.simulation.toyspring.simple;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by code4wt on 17/8/18.
 */
public class SimpleAOP {

    public static Object getProxy(Object bean, InvocationHandler invocationHandler) {

        /**
         *  ClassLoader loader,
            Class<?>[] interfaces,
            InvocationHandler h
         */

        return Proxy.newProxyInstance(SimpleAOP.class.getClassLoader(), bean.getClass().getInterfaces(), invocationHandler);
    }
}