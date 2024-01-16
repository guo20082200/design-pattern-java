package com.zishi.junit.ch02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;


/**
 * 自定义的Conditions
 *
 * 以下几个特定情况，condition method必须是静态的：
 * 1. 当 @EnabledIf or @DisabledIf 在类层面上使用的时候
 * 2. 当 @EnabledIf or @DisabledIf 用在 @ParameterizedTest 或者 @TestTemplate 方法上的时候
 * 3. 当 condition的方法属于外部类的时候
 *
 * */
@EnabledIf("com.zishi.junit.ch02.ExternalCondition#customCondition")
public class Condition08Test {
    @Test
    @EnabledIf("com.zishi.junit.ch02.ExternalCondition#customCondition")
    void enabled() {
        System.out.println("EnabledIf");
    }
}

class ExternalCondition {
    // 这里必须是静态方法
    static boolean customCondition() {
        return true;
    }
}
