package com.titizz.simulation.toyspring.simple;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lei.X
 * @date 2018/9/17
 */

public class Test {

    public static interface Foo {
        void test();
        void test2();
    }


    public static class  FooImpl implements Foo{

        @Override
        public void test() {
            System.out.println("test");
        }

        @Override
        public void test2() {
            System.out.println("test2");
        }
    }

    public static class MyInvocationHandler implements InvocationHandler {



        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("proxy method!");
            return null;
        }

    }

    /**
     * 自定义代理类
     */
    public static class ProxyInvocationHandler implements InvocationHandler{

        private Object object;
        private MethodInvocation beforeHandler;
        private MethodInvocation afterHandler;

        public ProxyInvocationHandler(Object object,MethodInvocation beforeHandler,MethodInvocation afterHandler){
            this.object = object;
            this.beforeHandler = beforeHandler;
            this.afterHandler = afterHandler;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            beforeHandler.invoke();
            Object result = method.invoke(object,args);
            afterHandler.invoke();
            return result;
        }
    }

    public static void main(String[] args) throws Exception {

        //设置saveGeneratedFiles值为true生成class字节码到文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");


        /**
         * 代理类只会实现MyInvocationHandler中的内容
         * 所以f.test()的结果为"proxy method"
         */
        Foo f = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(), new Class<?>[]{Foo.class}, new MyInvocationHandler());
        f.test();

        /**
         * 在委托类的前后添加了logTask和afterLog
         */
        MethodInvocation logTask = () -> System.out.println("log task start");
        MethodInvocation afterLog = () -> System.out.println("after start");

        FooImpl foo = new FooImpl();
        InvocationHandler invocationHandler = new ProxyInvocationHandler(foo,logTask,afterLog);
        /**
         * 当调用代理类对象的方法时，这个调用会被转送到invoke方法中，代理类对象作为proxy，参数method标识了具体调用的方法，args为这个方法的参数
         * 所以下面代码的f2.test()会传入到invocationHandler的invoke中，f2位proxy，test()为method
         */
        Foo f2 = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(), new Class<?>[]{Foo.class}, invocationHandler);
        f2.test();

    }
}


