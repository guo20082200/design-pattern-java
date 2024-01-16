package com.zishi.junit.ch02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 针对一个测试类，DisplayName按照下面四个规则展示：
 * 1. @DisplayName注解的值，如果有的话
 * 2. 通过调用@DisplayNameGeneration注解里面的指定的 DisplayNameGenerator，如果有的话
 * 3. 通过调用 DisplayNameGenerator默认的配置，通过配置参数，如果有的话
 * 4. 通过调用 org.junit.jupiter.api.DisplayNameGenerator.Standard
 */
@DisplayName("aaaaaaaaaaa")
class DisplayNameGeneratorDemoTest {

    @Nested
    @DisplayNameGeneration(ReplaceUnderscores.class)
    class A_year_is_not_supported {

        @Test
        void if_it_is_zero() {
        }

        @DisplayName("A negative value for year is not supported by the leap year computation.")
        @ParameterizedTest(name = "For example, year {0} is not supported.")
        @ValueSource(ints = { -1, -4 , -8})
        void if_it_is_negative(int year) {
            System.out.println(year);
        }

    }

    @Nested
    @IndicativeSentencesGeneration(separator = " -> ", generator = ReplaceUnderscores.class)
    class A_year_is_a_leap_year {

        @Test
        void if_it_is_divisible_by_4_but_not_by_100() {
        }

        @ParameterizedTest(name = "Year {0} is a leap year.")
        @ValueSource(ints = { 2016, 2020, 2048 })
        void if_it_is_one_of_the_following_years(int year) {
            System.out.println(year);
        }

    }

}