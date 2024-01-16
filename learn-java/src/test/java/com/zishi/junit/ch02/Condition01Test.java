package com.zishi.junit.ch02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * 基于操作系统的测试
 */
public class Condition01Test {

    @Test
    @EnabledOnOs(OS.MAC)
    void onlyOnMacOs() {

        System.out.println("仅仅再MAC平台下测试");
    }

    @TestOnMac
    @DisplayName("使用自定义的条件")
    void testOnMac() {
        System.out.println("使用自定义的条件测试");
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void onLinuxOrMac() {
        System.out.println("仅仅再LINUX，MAC平台下测试");
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void notOnWindows() {
        System.out.println("仅仅再WINDOWS平台下测试");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void OnWindows() {
        System.out.println("仅仅再WINDOWS平台下测试");
    }
}
