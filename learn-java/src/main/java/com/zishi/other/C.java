package com.zishi.other;


import com.zishi.spi.PrintService;

import java.util.ServiceLoader;

public class C {

    public static void main(String[] args) {
        ServiceLoader<PrintService> serviceLoader = ServiceLoader.load(PrintService.class);
        for (PrintService printService : serviceLoader) {
            printService.printInfo();
        }

    }
}
