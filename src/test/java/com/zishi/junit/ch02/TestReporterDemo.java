package com.zishi.junit.ch02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

import java.util.HashMap;
import java.util.Map;

/**
 * 如果方法参数为TestReporter类型，则TestReporterParameterResolver将提供TestReporter的实例。
 * 可以使用TestReporter发布关于当前测试运行的其他数据。
 * 数据可以通过TestExecutionListener.reportingEntryPublished()来使用，因此可以由ide查看或包含在报表中。
 */
@DisplayName("TestReporterDemo")
class TestReporterDemo {

    @Test
    void reportSingleValue(TestReporter testReporter) {
        testReporter.publishEntry("a status message");
    }

    @Test
    void reportKeyValuePair(TestReporter testReporter) {
        testReporter.publishEntry("a key", "a value");
    }

    @Test
    void reportMultipleKeyValuePairs(TestReporter testReporter) {
        Map<String, String> values = new HashMap<>();
        values.put("user name", "dk38");
        values.put("award year", "1974");
        testReporter.publishEntry(values);
    }
}