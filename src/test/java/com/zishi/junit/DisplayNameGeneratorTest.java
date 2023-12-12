package com.zishi.junit;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
public class DisplayNameGeneratorTest {

    @Test
    public void test_display_ok(){

        System.out.println("test_display_ok");
    }
}