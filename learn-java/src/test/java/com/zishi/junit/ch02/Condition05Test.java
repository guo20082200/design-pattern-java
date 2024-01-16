package com.zishi.junit.ch02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;


/**
 * System Property Conditions
 * 基于系统属性的测试
 */
public class Condition05Test {

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void onlyOn64BitArchitectures() {

        System.out.println("11111111111");
    }

    @Test
    @DisabledIfSystemProperty(named = "ci-server", matches = "true")
    void notOnCiServer() {
        System.out.println("DisabledIfSystemProperty");
    }
}
