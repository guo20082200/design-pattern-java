package com.zishi.guava.utility01;

import com.google.common.primitives.Ints;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Bytes/Shorts/Ints/Iongs/Floats/Doubles/Chars/Booleans
 */
public class BasicTest {


    @Test
    @DisplayName("测试 CharMatcher 成员方法")
    public void testMemberMethod() {

        List<Integer> list = Ints.asList(1, 2, 3);
        int compare = Ints.compare(1, 2);
        int i = Ints.checkedCast(300L);
        int min = Ints.min(1, 2, 3, 4, 5, 6, 7);

    }
}
