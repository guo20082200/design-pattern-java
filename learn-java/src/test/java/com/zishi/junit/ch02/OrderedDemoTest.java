package com.zishi.junit.ch02;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


/**
 * MethodOrderer 该接口有几个内部的实现类
 * public static class Random implements MethodOrderer：按照伪随机的顺序，支持自定义的种子
 * public static class OrderAnnotation implements MethodOrderer：按照注解（通过方法的@Order注解）排序，
 * public static class DisplayName implements MethodOrderer：按照DisplayName排序（按照字母表的顺序）
 * public static class MethodName implements MethodOrderer： 按照方法名排序（按照字母表的顺序）
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderedDemoTest {
    @Test
    @Order(1)
    void nullValues() {
        System.out.println("perform assertions against null values");
    }

    @Test
    @Order(2)
    void emptyValues() {
        System.out.println("perform assertions against empty values");
    }

    @Test
    @Order(3)
    void validValues() {
        System.out.println("perform assertions against valid values");
    }
}
