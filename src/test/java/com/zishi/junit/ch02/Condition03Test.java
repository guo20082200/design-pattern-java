package com.zishi.junit.ch02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;


/**
 * 基于jdk版本的测试
 */
public class Condition03Test {

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void onlyOnJava8() {
        System.out.println("基于java8的测试");
    }

    @Test
    @EnabledOnJre({JRE.JAVA_9, JRE.JAVA_10})
    void onJava9Or10() {
        System.out.println("基于java9， java10 的测试");
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_9, max = JRE.JAVA_11)
    void fromJava9to11() {
        System.out.println("基于最小java9， 最大java11 的测试");
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_9)
    void fromJava9toCurrentJavaFeatureNumber() {
        System.out.println("基于最小java9的测试");
    }
    @Test
    @EnabledForJreRange(max = JRE.JAVA_11)
    void fromJava8To11() {
        System.out.println("基于最大java11的测试");
    }
    @Test
    @DisabledOnJre(JRE.JAVA_9)
    void notOnJava9() {
        System.out.println("不是基于java9的测试");
    }
    @Test
    @DisabledForJreRange(min = JRE.JAVA_9, max = JRE.JAVA_11)
    void notFromJava9to11() {
        System.out.println("不是基于java9到java11的测试");
    }

    @Test
    @DisabledForJreRange(min = JRE.JAVA_17)
    void notFromJava9toCurrentJavaFeatureNumber() {
        System.out.println("jdk版本高于java17的不进行测试");
    }

    @Test
    @DisabledForJreRange(max = JRE.JAVA_10)
    void notFromJava8to11() {
        System.out.println("jdk版本低于java10的不进行测试");
    }

}
