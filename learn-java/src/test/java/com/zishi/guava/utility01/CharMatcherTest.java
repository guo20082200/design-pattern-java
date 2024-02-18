package com.zishi.guava.utility01;

import com.google.common.base.CharMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * CharMatcher，将字符的匹配和处理解耦，并提供丰富的方法供你使用！
 *
 *
 *
 */
public class CharMatcherTest {


    /**
     * CharMatcher is(char match): 返回匹配指定字符的Matcher
     *  CharMatcher isNot(char match): 返回不匹配指定字符的Matcher
     *  CharMatcher anyOf(CharSequence sequence): 返回匹配sequence中任意字符的Matcher
     *  CharMatcher noneOf(CharSequence sequence): 返回不匹配sequence中任何一个字符的Matcher
     *  CharMatcher inRange(char startInclusive, char endIncludesive): 返回匹配范围内任意字符的Matcher
     *  CharMatcher forPredicate(Predicate<? super Charater> predicate): 返回使用predicate的apply()判断匹配的Matcher
     *  CharMatcher negate(): 返回以当前Matcher判断规则相反的Matcher
     *  CharMatcher and(CharMatcher other): 返回与other匹配条件组合做与来判断的Matcher
     *  CharMatcher or(CharMatcher other): 返回与other匹配条件组合做或来判断的Matcher
     *  boolean matchesAnyOf(CharSequence sequence): 只要sequence中有任意字符能匹配Matcher,返回true
     *  boolean matchesAllOf(CharSequence sequence): sequence中所有字符都能匹配Matcher,返回true
     *  boolean matchesNoneOf(CharSequence sequence): sequence中所有字符都不能匹配Matcher,返回true
     *  int indexIn(CharSequence sequence): 返回sequence中匹配到的第一个字符的坐标
     *  int indexIn(CharSequence sequence, int start): 返回从start开始,在sequence中匹配到的第一个字符的坐标
     *  int lastIndexIn(CharSequence sequence): 返回sequence中最后一次匹配到的字符的坐标
     *  int countIn(CharSequence sequence): 返回sequence中匹配到的字符计数
     *  String removeFrom(CharSequence sequence): 删除sequence中匹配到到的字符并返回
     *  String retainFrom(CharSequence sequence): 保留sequence中匹配到的字符并返回
     *  String replaceFrom(CharSequence sequence, char replacement): 替换sequence中匹配到的字符并返回
     *  String trimFrom(CharSequence sequence): 删除首尾匹配到的字符并返回
     *  String trimLeadingFrom(CharSequence sequence): 删除首部匹配到的字符
     *  String trimTrailingFrom(CharSequence sequence): 删除尾部匹配到的字符
     *
     *
     * @throws IOException
     */
    @Test
    @DisplayName("测试 CharMatcher 静态方法")
    public void test() throws IOException {

        // String collapseFrom(CharSequence sequence, char replacement): 将匹配到的组(连续匹配的字符)替换成replacement



        // String trimAndCollapseFrom(CharSequence sequence, char replacement): 先trim在replace
        CharMatcher any = CharMatcher.any();
        System.out.println(any.toString());
        String s = any.trimAndCollapseFrom("  aaa---rrrr  ", '-');
        System.out.println(s);
    }

    @Test
    @DisplayName("测试 CharMatcher 成员方法")
    public void testMemberMethod() throws IOException {



        // String trimAndCollapseFrom(CharSequence sequence, char replacement): 先trim在replace
        CharMatcher any = CharMatcher.any();
        String s = any.trimAndCollapseFrom("aaa---rrrr", '-');
        System.out.println(s);
    }

    /**
     * 默认实现类
     *  CharMatcher本身提供了很多CharMatcher实现类,如下:
     *  ANY: 匹配任何字符
     *  ASCII: 匹配是否是ASCII字符
     *  BREAKING_WHITESPACE: 匹配所有可换行的空白字符(不包括非换行空白字符,例如"\u00a0")
     *  DIGIT: 匹配ASCII数字
     *  INVISIBLE: 匹配所有看不见的字符
     *  JAVA_DIGIT: 匹配UNICODE数字, 使用 Character.isDigit() 实现
     *  JAVA_ISO_CONTROL: 匹配ISO控制字符, 使用 Charater.isISOControl() 实现
     *  JAVA_LETTER: 匹配字母, 使用 Charater.isLetter() 实现
     *  JAVA_LETTER_OR_DIGIT: 匹配数字或字母
     *  JAVA_LOWER_CASE: 匹配小写
     *  JAVA_UPPER_CASE: 匹配大写
     *  NONE: 不匹配所有字符
     *  SINGLE_WIDTH: 匹配单字宽字符, 如中文字就是双字宽
     *  WHITESPACE: 匹配所有空白字符
     * @throws IOException
     */
    @Test
    @DisplayName("使用 CharMatcher 预定义常量")
    public void testPreDefine() throws IOException {

        CharMatcher charMatcher = CharMatcher.is(',');

        String str = "FirstName LastName +1 123 456 789 !@#$%^&*()_+|}{:\"?><";
        CharMatcher digit = CharMatcher.digit();
        //CharMatcher digit = CharMatcher.Digit.INSTANCE;
        //System.out.println(digit.retainFrom(str)); // 1123456789
        Assertions.assertEquals("1123456789", digit.retainFrom(str));
        Assertions.assertEquals(10, digit.countIn(str));
        CharMatcher javaLetter = CharMatcher.javaLetter();
        Assertions.assertEquals("FirstNameLastName", javaLetter.retainFrom(str));
        CharMatcher javaLetterOrDigit = CharMatcher.javaLetterOrDigit();
        Assertions.assertEquals("FirstNameLastName1123456789", javaLetterOrDigit.retainFrom(str));
        CharMatcher any = CharMatcher.any();
        Assertions.assertEquals(54, any.countIn(str));

        //INVISIBLE
        CharMatcher invisible = CharMatcher.invisible();
    }
}
