package com.zishi.junit.ch02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ParameterizedDemo02Test {

    /**
     * 定制化的DisplayName
     * @param fruit
     * @param rank
     */
    @DisplayName("Display name of container")
    @ParameterizedTest(name = "{index} ==> the rank of ''{0}'' is {1}")
    @CsvSource({ "apple, 1", "banana, 2", "'lemon, lime', 3" })
    void testWithCustomDisplayNames(String fruit, int rank) {
        System.out.println(fruit + " " + rank);
    }


    @DisplayName("A parameterized test with named arguments")
    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("namedArguments")
    void testWithNamedArguments(File file) {
    }
    static Stream<Arguments> namedArguments() {
        return Stream.of(
                arguments(named("An important file", new File("path1"))),
                arguments(named("Another file", new File("path2")))
        );
    }
}
