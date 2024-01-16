package com.zishi.junit.ch02;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
@Tag("model")
public class TaggingDemoTest {

    @Test
    @Tag("taxes")
    void testingTaxCalculation() {
        System.out.println(".............");
    }
}
