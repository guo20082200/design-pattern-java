package com.zishi.guava.utility01;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SplitterTest {

    private static final Splitter splitter = Splitter.on(","); // .skipNulls();

    /**
     *   private Splitter(Strategy strategy) {
     *     this(strategy, false, CharMatcher.none(), Integer.MAX_VALUE);
     *   }
     *
     *   private Splitter(Strategy strategy, boolean omitEmptyStrings, CharMatcher trimmer, int limit) {
     *     this.strategy = strategy;
     *     this.omitEmptyStrings = omitEmptyStrings;
     *     this.trimmer = trimmer;
     *     this.limit = limit;
     *   }
     * @throws IOException
     */
    @Test
    @DisplayName("测试 Splitter 静态方法")
    public void testOn() throws IOException {
        // public static Splitter on(char separator)

        // public static Splitter on(final CharMatcher separatorMatcher)
        // public static Splitter on(final String separator)
        // public static Splitter on(Pattern separatorPattern)
        // private static Splitter on(final CommonPattern separatorPattern) -- 私有的

        Splitter on = Splitter.on(",");

    }

    @Test
    @DisplayName("测试trimResults方法")
    public void testTrimResults() throws IOException {

        Iterable<String> split = Splitter.on(',').trimResults().split(" a, b ,c ");
        System.out.println(split); // [a, b, c]

        List<String> strings = Splitter.on(',').trimResults().splitToList(" a, b ,c ");
        System.out.println(strings); // [a, b, c]

        Stream<String> stream = Splitter.on(',').trimResults().splitToStream(" a, b ,c ");
        System.out.println(stream.collect(Collectors.toList()));
    }


    @Nested
    @DisplayName("测试MapSplitterTest方法")
    public class MapSplitterTest {

        @Test
        @DisplayName("测试Splitter")
        public void testOn() throws IOException {
            Splitter.MapSplitter mapSplitter = Splitter.on(",").withKeyValueSeparator("-");
            String s = "a-1,b-2";
            Map<String, String> split = mapSplitter.split(s);
            System.out.println(split); // {a=1, b=2}
        }

    }
}
