package com.zishi.junit.ch02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * 基于芯片指令集的测试
 */
public class Condition02Test {

    @EnabledOnOs(architectures = "aarch64")
    void onAarch64() {
        // ...
    }

    @Test
    @DisabledOnOs(architectures = "x86_64")
    void notOnX86_64() {
        // ...
    }

    @Test
    @EnabledOnOs(value = OS.MAC, architectures = "aarch64")
    void onNewMacs() {
        // ...
    }

    @Test
    @DisabledOnOs(value = OS.MAC, architectures = "aarch64")
    void notOnNewMacs() {
        // ...
    }
}
