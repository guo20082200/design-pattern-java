package com.zishi.junit.ch02;

import org.junit.jupiter.api.*;

/**
 * ClassOrderer有几个实现类：
 * public static class Random implements ClassOrderer：按照伪随机的顺序，支持自定义的种子
 * public static class OrderAnnotation implements ClassOrderer：按照注解（通过方法的@Order注解）排序，
 * public static class DisplayName implements ClassOrderer：根据类DisplayName按照字母表排序
 * public static class ClassName implements ClassOrderer：根据类全限定名按照字母表排序
 *
 */
// 类 测试@Nested排序的注解
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class OrderedNestedClassesDemoTest {
    @Nested
    @Order(1)
    class PrimaryTests {
        @Test
        void test1() {
            System.out.println("111111111111111");
        }

        @Nested
        class PrimaryTests01 {
            @Test
            void test2() {
                System.out.println("sssssssssss");
            }
        }
    }

    @Nested
    @Order(2)
    class SecondaryTests {
        @Test
        void test2() {
            System.out.println("2222222222222222");
        }
    }
}