package org.example.jucdemo2.threadlocal;

import java.util.function.Supplier;

public class ThreadLocalDemo01Test {

    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
    }
}
