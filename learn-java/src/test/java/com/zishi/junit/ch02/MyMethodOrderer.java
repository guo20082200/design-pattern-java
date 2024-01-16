package com.zishi.junit.ch02;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;

import java.util.Comparator;

public class MyMethodOrderer implements MethodOrderer {

    private static final Comparator<MethodDescriptor> comparator = Comparator.comparing(MethodDescriptor::getDisplayName);

    @Override
    public void orderMethods(MethodOrdererContext context) {
        context.getMethodDescriptors().sort(comparator);
    }
}
