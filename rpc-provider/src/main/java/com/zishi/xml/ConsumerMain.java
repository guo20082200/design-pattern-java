package com.zishi.xml;

import com.zishi.xml.provider.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerMain {


    public static void main(String[] args) {
        System.setProperty("zookeeper.sasl.client", "false");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService");
        String hello = demoService.sayHello("world");
        System.out.println(hello);
    }

}
