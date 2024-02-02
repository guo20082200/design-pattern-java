package com.zishi.spi;

import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

public class SPITest {

    @Test
    void createTest() {
        ServiceLoader<PrintService> loader = ServiceLoader.load(PrintService.class);

        for (PrintService service : loader) {
            service.printInfo();
        }
    }
}
