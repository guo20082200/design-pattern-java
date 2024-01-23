package com.zishi.bootstrap.api;

import com.zishi.xml.provider.DemoService;
import com.zishi.xml.provider.DemoServiceImpl;
import com.zishi.xml.provider.FooService;
import com.zishi.xml.provider.FooServiceImpl;
import org.apache.dubbo.config.*;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;


public class DemoProvider {
    public static void main(String[] args) {

        System.setProperty("zookeeper.sasl.client", "false");
        ConfigCenterConfig configCenter = new ConfigCenterConfig();
        configCenter.setAddress("zookeeper://127.0.0.1:2181");

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(12345);
        protocol.setThreads(200);

        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
        // 服务提供者暴露服务配置
        ServiceConfig<DemoService> demoServiceConfig = new ServiceConfig<>();
        demoServiceConfig.setInterface(DemoService.class);
        demoServiceConfig.setRef(new DemoServiceImpl());
        demoServiceConfig.setVersion("1.0.0");

        // 第二个服务配置
        ServiceConfig<FooService> fooServiceConfig = new ServiceConfig<>();
        fooServiceConfig.setInterface(FooService.class);
        fooServiceConfig.setRef(new FooServiceImpl());
        fooServiceConfig.setVersion("1.0.0");


        // 通过DubboBootstrap简化配置组装，控制启动过程
        DubboBootstrap.getInstance()
                .application("demo-provider") // 应用配置
                .registry(new RegistryConfig("zookeeper://172.16.23.77:2181")) // 注册中心配置
                .protocol(protocol) // 全局默认协议配置
                .service(demoServiceConfig) // 添加ServiceConfig
                .service(fooServiceConfig)
                .start()    // 启动Dubbo
                .await();   // 挂起等待(防止进程退出）
    }
}