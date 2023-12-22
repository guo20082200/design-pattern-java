package com.zishi.junit.ch02;

import org.junit.jupiter.api.Test;

public class ComposedAnnotationsTest {
    @Fast
    @Test
    void myFastTest() {

        System.out.println("myFastTest");
    }
}
