package com.zishi.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ProviderMain {

    public static void main(String[] args) throws IOException {

        System.setProperty("zookeeper.sasl.client", "false");
        //加载xml配置文件启动
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
        context.start();
        System.in.read(); // 按任意键退出

    }
}
