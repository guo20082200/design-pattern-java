package com.zishi.junit.ch02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;


/**
 * 自定义的Conditions
 */
public class Condition07Test {
    @Test
    @EnabledIf("customCondition")
    void enabled() {
        System.out.println("EnabledIf");
    }

    @Test
    @DisabledIf("customCondition02")
    void disabled() {
        System.out.println("DisabledIf");
    }

    boolean customCondition() {
        return true;
    }
    boolean customCondition02() {
        return false;
    }

}
