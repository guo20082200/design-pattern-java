package com.zishi.junit.ch02;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * 构造器和方法的参数注入
 * <p>
 * TestInfoParameterResolver：该解析器是自动注册的
 * <p>
 * TestInfoParameterResolver
 * 如果一个方法参数是TestInfo类型，
 * 那么TestInfoParameterResolver将提供一个与当前测试对应的TestInfo实例作为参数的值。
 * 然后，TestInfo可以用来检索关于当前测试的信息，比如测试的显示名称、测试类、测试方法或相关的标记。
 * DisplayName 可以是技术名称，例如测试类或测试方法的名称，也可以是通过 @DisplayName 配置的自定义名称。
 * <p>
 * TestInfo作为来自JUnit 4的TestName规则的drop-in替换。
 */
@DisplayName("TestInfo Demo")
class TestInfoDemoTest {
    TestInfoDemoTest(TestInfo testInfo) {
        assertEquals("TestInfo Demo", testInfo.getDisplayName());
    }

    @BeforeEach
    void init(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        assertTrue(displayName.equals("TEST 1") || displayName.equals("test2()"));
    }

    @Test
    @DisplayName("TEST 1")
    @Tag("my-tag")
    void test1(TestInfo testInfo) {
        assertEquals("TEST 1", testInfo.getDisplayName());
        assertTrue(testInfo.getTags().contains("my-tag"));
    }

    @Test
    @DisplayName("test2")
    @Tag("my-tag2")
    void test2(TestInfo testInfo) {
        //String displayName = testInfo.getDisplayName();
       // System.out.println(displayName);
        //assertEquals("test2", displayName);
        assertTrue(testInfo.getTags().contains("my-tag2"));
    }
}