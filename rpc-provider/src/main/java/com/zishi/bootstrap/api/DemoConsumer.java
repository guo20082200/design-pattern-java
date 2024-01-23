package com.zishi.bootstrap.api;

import com.zishi.xml.provider.DemoService;
import com.zishi.xml.provider.FooService;
import org.apache.dubbo.config.*;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;


public class DemoConsumer {
    public static void main(String[] args) {

        System.setProperty("zookeeper.sasl.client", "false");
        // 引用远程服务
        ReferenceConfig<DemoService> demoServiceReference = new ReferenceConfig<>();
        demoServiceReference.setInterface(DemoService.class);
        demoServiceReference.setVersion("1.0.0");

        ReferenceConfig<FooService> fooServiceReference = new ReferenceConfig<>();
        fooServiceReference.setInterface(FooService.class);
        fooServiceReference.setVersion("1.0.0");

        // 通过DubboBootstrap简化配置组装，控制启动过程
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application("demo-consumer") // 应用配置
                .registry(new RegistryConfig("zookeeper://172.16.23.77:2181")) // 注册中心配置
                .reference(demoServiceReference) // 添加ReferenceConfig
                .reference(fooServiceReference)
                .start();    // 启动Dubbo

        // 和本地bean一样使用demoService
        // 通过Interface获取远程服务接口代理，不需要依赖ReferenceConfig对象
        DemoService demoService = DubboBootstrap.getInstance().getCache().get(DemoService.class);
        demoService.sayHello("Dubbo");

        FooService fooService = DubboBootstrap.getInstance().getCache().get(FooService.class);
        fooService.greeting("Dubbo");
    }

}