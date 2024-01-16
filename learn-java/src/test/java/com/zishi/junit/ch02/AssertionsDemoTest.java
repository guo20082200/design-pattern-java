package com.zishi.junit.ch02;

import com.zishi.junit.Calculator;
import com.zishi.junit.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Assertions测试样例")
class AssertionsDemoTest {
    private final Calculator calculator = new Calculator();
    private final Person person = new Person("Jane", "Doe");

    @Test
    void standardAssertions() {
        assertEquals(2, calculator.add(1, 1));
        assertEquals(4, calculator.multiply(2, 2), "The optional failure message is now the last parameter");
        assertTrue('a' < 'b', () -> "Assertion messages can be lazily evaluated -- " + "to avoid constructing complex messages unnecessarily.");
    }

    @Test
    void groupedAssertions() {
        // In a grouped assertion all assertions are executed, and all
        // failures will be reported together.
        Assertions.assertAll("person", () -> assertEquals("Jane", person.getFirstName()), () -> assertEquals("Doe", person.getLastName()));
    }

    @Test
    void dependentAssertions() {
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
        assertAll("properties", () -> {
            String firstName = person.getFirstName();
            assertNotNull(firstName);
            // Executed only if the previous assertion is valid.
            assertAll("first name",
                    () -> assertTrue(firstName.startsWith("J")),
                    () -> assertTrue(firstName.endsWith("e")));
        }, () -> {
            // Grouped assertion, so processed independently
            // of results of first name assertions.
            String lastName = person.getLastName();
            assertNotNull(lastName);
            // Executed only if the previous assertion is valid.
            assertAll("last name",
                    () -> assertTrue(lastName.startsWith("D")),
                    () -> assertTrue(lastName.endsWith("e")));
        });
    }


    @Test
    void exceptionTesting() {
        Exception exception = assertThrows(ArithmeticException.class, () ->
                calculator.divide(1, 0));
        assertEquals("/ by zero", exception.getMessage());
    }


    @Test
    void timeoutNotExceeded() {
        // The following assertion succeeds.
        assertTimeout(Duration.ofSeconds(2), () -> {
            // 执行的任务少于2s
            Thread.sleep(1000);
        }); // execution exceeded timeout of 2000 ms by 1004 ms
    }

    @Test
    void timeoutNotExceededWithResult() {
        // The following assertion succeeds, and returns the supplied object.
        String actualResult = assertTimeout(Duration.ofSeconds(2), () -> "a result");
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutNotExceededWithMethod() {
        // The following assertion invokes a method reference and returns an object.
        String actualGreeting = assertTimeout(Duration.ofSeconds(2), AssertionsDemoTest::greeting);
        assertEquals("Hello, World!", actualGreeting);
    }

    @Test
    void timeoutExceeded() {
        // The following assertion fails with an error message similar to:
        // execution exceeded timeout of 10 ms by 91 ms
        assertTimeout(Duration.ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }

    /**
     *
     */
    @Test
    void timeoutExceededWithPreemptiveTermination() {
        // The following assertion fails with an error message similar to:
        // execution timed out after 10 ms

        /**
         * Assertions.assertTimeoutPreemptively 这个方法可以提供executable 或者 supplier在不同的线程里面
         * 该行为可以导致意想不到的副作用，如果代码是基于 java.lang.ThreadLocal存储并在 executable 或者 supplier的执行的
         *
         * 一个常见的例子，Spring 框架针对事务测试的支持
         * 特别的：Spring 测试框架 在测试方法调用之前，支持绑定事务到当前线程上（通过ThreadLocal），
         * 这就会导致以下的结果：如果一个由 Assertions.assertTimeoutPreemptively方法提供的 executable 或者 supplier
         * 调用了Spring管理的组件并且参与了事务，这些组件的任何的动作在Spring的test-managed事务管理中都不会回滚。
         * 相反，这些动作都会被持久化到数据库(例如：关系型数据库), 尽管test-managed事务已经回滚了。
         *
         * 只要是基于ThreadLocal的，那么相同的结果也会在其他的框架中出现
         *
         */
        assertTimeoutPreemptively(Duration.ofMillis(10), () -> {
            // 模拟任务超过10ms
            new CountDownLatch(1).await();
        });
    }

    private static String greeting() {
        return "Hello, World!";
    }
}