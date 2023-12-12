package com.zishi.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Disabled
@DisplayName("A special HelloWorldTest case")
class HelloWorld2Test {


    @BeforeAll
    static void setUpClass() {
        System.out.println("BeforeAll");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("AfterAll");
    }

    @BeforeEach
    void setUp() {
        System.out.println("@BeforeEach，测试开始");
    }

    @AfterEach
    void tearDown() {
        System.out.println("@AfterEach，测试结束");
    }

    @Test
    @DisplayName("sayHello")
    void sayHello() {
        HelloWorld helloWorld = new HelloWorld();
        String result = helloWorld.sayHello();
        System.out.println(result);
        //assertEquals("您好，欢迎访问xxx", result);
        System.out.println("1111111111111111");
    }

    @Test
    @DisplayName("\uD83D\uDE31 ╯°□°）╯")
    void sayHello2() {
        HelloWorld helloWorld = new HelloWorld();
        String result = helloWorld.sayHello();
        System.out.println(result);
        assertEquals("您好，欢迎访问xxx", result);
        System.out.println("2222222222222222222");
    }
}