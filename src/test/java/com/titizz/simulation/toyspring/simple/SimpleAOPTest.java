package com.titizz.simulation.toyspring.simple;

import com.titizz.simulation.toyspring.HelloService;
import com.titizz.simulation.toyspring.HelloServiceImpl;
import org.junit.Test;

/**
 * Created by code4wt on 17/8/19.
 */
public class SimpleAOPTest {

    @Test
    public void getProxy() throws Exception {
        // 1. 创建一个 MethodInvocation 实现类
        MethodInvocation logTask = () -> System.out.println("log task start");
        MethodInvocation afterLog = () -> System.out.println("after start");
        HelloServiceImpl helloServiceImpl = new HelloServiceImpl();

        // 2. 创建一个 Advice
        /**
         * 代理类
         * 是InvocationHandler的继承类
         */
        Advice beforeAdvice = new BeforeAdvice(helloServiceImpl, logTask,afterLog);

        // 3. 为目标对象生成代理
        HelloService helloServiceImplProxy = (HelloService) SimpleAOP.getProxy(helloServiceImpl,beforeAdvice);

        helloServiceImplProxy.sayHelloWorld();
    }
}