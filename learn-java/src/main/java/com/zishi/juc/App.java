package com.zishi.juc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

//@SpringBootApplication
public class App {
    public static void main(String[] args) {
        //System.out.println("Hello World!");
        //SpringApplication.run(App.class, args);

        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        System.out.println(threadFactory.getClass());
    }
}
