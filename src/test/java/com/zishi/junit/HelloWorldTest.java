package com.zishi.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 先引入依赖：maven-surefire-plugin
 * 命令行运行test类：mvn -Dtest=HelloWorldTest clean install
 */
@DisplayName("A special HelloWorldTest case") //测试类或者方法的显示名称
class HelloWorldTest {


    @BeforeAll //表明在所有测试方法执行之前执行的方法，且只执行一次
    static void setUpClass() {
        System.out.println("BeforeAll");
    }

    @AfterAll //表明在所有测试方法执行之后执行的方法，且只执行一次
    static void tearDownClass() {
        System.out.println("AfterAll");
    }

    @BeforeEach //表明在每个测试方法执行之前都会执行的方法
    void setUp() {
        System.out.println("@BeforeEach，测试开始");
    }

    @AfterEach //表明在每个测试方法执行之后都会执行的风发
    void tearDown() {
        System.out.println("@AfterEach，测试结束");
    }

    @Test // 表明一个测试方法
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

    @Test
    @Disabled("给出来该方法不运行的理由") // 禁用测试类或者方法
    @DisplayName("\uD83D\uDE31 ╯°□°）╯")
    void sayHello3() {
        HelloWorld helloWorld = new HelloWorld();
        String result = helloWorld.sayHello();
        System.out.println(result);
        assertEquals("您好，欢迎访问xxx", result);
        System.out.println("3333333333333333333");
    }

    @RepeatedTest(3)
    @DisplayName("重复测试")
    void sayHello4() {
        System.out.println("3333333333333333333");
    }
}