package com.zishi.junit.ch02;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DisplayNameParamTest {

    @ParameterizedTest(name = "#{index} - Test with {0} and {1}")
    @MethodSource("argumentProvider")
    void test_method_multi(String str, int length) {
    }

    static Stream<Arguments> argumentProvider() {
        return Stream.of(
                arguments("abc", 3),
                arguments("lemon", 2)
        );
    }
}

