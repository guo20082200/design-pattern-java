package org.example.securitydemo.util;

public class IdTest {

    public static void main(String[] args) {

        IdWorker idWorker = new IdWorker(0, 0);
        long l = idWorker.nextId();
        System.out.println(l);
    }
}

// 1917031770025496576
// 1917031875260583936