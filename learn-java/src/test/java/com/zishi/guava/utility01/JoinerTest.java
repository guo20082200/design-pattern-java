package com.zishi.guava.utility01;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JoinerTest {

    private static final Joiner joiner = Joiner.on(","); // .skipNulls();

    @Test
    @DisplayName("测试Joiner 构造方法")
    public void testOn() throws IOException {
        Joiner joiner02 = Joiner.on(',').skipNulls();
        Assertions.assertNotNull(joiner02);
    }

    @Test
    @DisplayName("测试appendTo方法")
    public void testAppendTo() throws IOException {
        // public <A extends Appendable> A appendTo(A appendable, Iterable<? extends @Nullable Object> parts)
        // public <A extends Appendable> A appendTo(A appendable, Iterator<? extends @Nullable Object> parts) -- 核心方法
        // public final <A extends Appendable> A appendTo(A appendable, @Nullable Object[] parts)
        // public final <A extends Appendable> A appendTo(A appendable,@CheckForNull Object first,@CheckForNull Object second,@Nullable Object... rest)throws IOException

        // public final StringBuilder appendTo(StringBuilder builder, Iterable<? extends @Nullable Object> parts)
        // public final StringBuilder appendTo(StringBuilder builder, Iterator<? extends @Nullable Object> parts)
        // public final StringBuilder appendTo(StringBuilder builder, @Nullable Object[] parts)
        // public final StringBuilder appendTo(StringBuilder builder,@CheckForNull Object first,@CheckForNull Object second,@Nullable Object... rest)
        Appendable appendable = new StringBuilder();
        ArrayList<String> strings = Lists.newArrayList("a", "b", "c");
        joiner.appendTo(appendable, strings.iterator());
        System.out.println(appendable);
    }

    @Test
    @DisplayName("测试join方法")
    public void testJoin() throws IOException {
        // public final String join(@CheckForNull Object first, @CheckForNull Object second, @Nullable Object... rest)
        // public final String join(@Nullable Object[] parts)
        // public final String join(Iterator<? extends @Nullable Object> parts)
        // public final String join(Iterable<? extends @Nullable Object> parts)

        // join 最终也是调用的appendto方法
        // appendTo(new StringBuilder(), parts).toString()
        ArrayList<String> strings = Lists.newArrayList("a", "b", "c");
        String join = joiner.join(strings.iterator());
        //System.out.println(join); // a,b,c
        Assertions.assertEquals("a,b,c", join);
    }

    @Nested
    @DisplayName("测试MapJoinerTest方法")
    public class MapJoinerTest {

        @Test
        @DisplayName("测试Joiner 构造方法")
        public void testOn() throws IOException {
            Joiner.MapJoiner mapJoiner = joiner.withKeyValueSeparator("-");

            // public String join(Map<?, ?> map)

            HashMap<String, String> map = Maps.newHashMap();
            map.put("a", "1");
            map.put("b", "2");
            String join = mapJoiner.join(map);
            System.out.println(join); // a-1,b-2
            Assertions.assertEquals("a-1,b-2", join); //
        }

    }
}
