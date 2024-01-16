package com.zishi.junit;

/**
 * 业务类
 *
 * @author
 **/
public class HelloWorld {
    public String sayHello() {
        return "您好，欢迎访问xxx";
    }

    public static void main(String[] args) {
        System.out.println(new HelloWorld().sayHello());
    }
}