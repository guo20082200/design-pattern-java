package com.zishi.junit.hamrest;

import com.zishi.junit.Calculator;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestAssertionsDemoTest {


    private final Calculator calculator = new Calculator();

    private List<String> values;

    @BeforeEach
    public void setUp() {
        values = new ArrayList<>();
        values.add("a");
        values.add("b");
        values.add("c");
    }

    @Test
    void assert01() {
        assertThat(3, CoreMatchers.is(equalTo(values.size())));

        Matcher<String> matcher = CoreMatchers.containsString("a");

        assertThat(true, CoreMatchers.is(values.contains("a")));


        assertThat(values, Matchers.hasSize(3));
        assertThat(values, Matchers.hasItem("a"));
        assertThat(values,
                Matchers.hasItem(CoreMatchers.anyOf(CoreMatchers.equalTo("a"), CoreMatchers.equalTo("b"), CoreMatchers.equalTo("c"))));
    }

    @Test
    void assertWithHamcrestMatcher() {
        assertThat(calculator.subtract(4, 1), CoreMatchers.is(equalTo(3)));
    }

    @Test
    @DisplayName("Matchers 的所有方法")
    void assert03() {

        // 1. Double.isNaN(3.2)
        assertThat(Double.NaN, Matchers.notANumber());
        assertThat(3.2, Matchers.isA(Number.class));
        String[] arr = {};
        assertThat(arr, Matchers.emptyArray());

        assertThat("myValue", Matchers.allOf(startsWith("my"), containsString("Val")));


        assertThat("myValue", Matchers.anyOf(startsWith("foo"), containsString("Val")));

        assertThat("fab", both(containsString("a")).and(containsString("b")));
        assertThat("fan", either(containsString("a")).or(containsString("b")));

        BigDecimal myBigDecimal = new BigDecimal("0");

        describedAs("a big decimal equal to %0", equalTo(myBigDecimal), myBigDecimal.toPlainString());

        assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")));

        //assertThat("cheese", equalTo("smelly"));

        assertThat(Arrays.asList("foo", "bar"), hasItem(startsWith("ba")));

        assertThat("foo", equalTo("foo"));
        assertThat(new String[] {"foo", "bar"}, equalTo(new String[] {"foo", "bar"}));
        assertThat(new String("a"), instanceOf(Object.class));

        assertThat("cheese", is(not(equalTo("smelly"))));

        assertThat("cheese", is(not(nullValue())));
        assertThat("", is(not(nullValue())));

        assertThat("myStringOfNote", containsString("ring"));
        assertThat("myStringOfNote", containsStringIgnoringCase("Ring"));

        assertThat("myStringOfNote", startsWith("my"));
        assertThat("myStringOfNote", startsWithIgnoringCase("My"));

        assertThat("myStringOfNote", endsWith("Note"));
        assertThat("myStringOfNote", endsWithIgnoringCase("note"));

        //assertThat("abc", matchesRegex(Pattern.compile("ˆ[a-z]$")));

        assertThat(new Integer[]{1,2,3}, is(array(equalTo(1), equalTo(2), equalTo(3))));

        assertThat(new String[] {"foo", "bar"}, hasItemInArray(startsWith("ba")));

        assertThat(new String[]{"foo", "bar"}, arrayContaining("foo", "bar"));

        assertThat(new String[]{"foo", "bar"}, arrayContainingInAnyOrder(equalTo("bar"), equalTo("foo")));

        assertThat(new String[]{"foo", "bar"}, arrayWithSize(equalTo(2)));
        assertThat(new String[0], emptyArray());

        Map<String, String> myMap = new HashMap<>();
        myMap.put("a", "1");
        myMap.put("b", "1");
        assertThat(myMap, is(aMapWithSize(equalTo(2))));

        Map<String, String> myMap2 = new HashMap<>();
        assertThat(myMap2, is(anEmptyMap()));

        assertThat(Arrays.asList("foo", "bar"), hasSize(2));

        assertThat(new ArrayList<String>(), is(empty()));

        assertThat(new ArrayList<String>(), is(emptyCollectionOf(String.class)));

        assertThat(Arrays.asList("foo", "bar"), contains("foo", "bar"));

        Map<String, String> myMap3 = new HashMap<>();
        myMap3.put("bar", "foo");
        myMap3.put("foo", "e");
        assertThat(myMap3, hasEntry(equalTo("bar"), equalTo("foo")));

        assertThat(myMap3, hasKey(equalTo("bar")));

        assertThat(myMap3, hasValue(equalTo("foo")));

        assertThat("foo", is(in(new String[]{"bar", "foo"})));

        assertThat("foo", isIn(Arrays.asList("bar", "foo")));

        assertThat("foo", isOneOf("bar", "foo"));
        assertThat("foo", is(oneOf("bar", "foo")));
        //assertThat(1.03, is(closeTo(1.0, 0.03)));
        assertThat(Double.NaN, is(notANumber()));
        assertThat(1, comparesEqualTo(1));
        assertThat(2, greaterThan(1));
        assertThat(1, greaterThanOrEqualTo(1));
        assertThat(1, lessThan(2));
        assertThat(1, lessThanOrEqualTo(1));
        assertThat("Foo", equalToIgnoringCase("FOO"));
        assertThat("   my\tfoo  bar ", equalToIgnoringWhiteSpace(" my  foo bar"));
        assertThat(((String)null), is(emptyOrNullString()));
    }
}
