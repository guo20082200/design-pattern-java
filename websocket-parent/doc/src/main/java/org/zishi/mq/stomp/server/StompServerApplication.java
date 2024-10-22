package org.zishi.mq.stomp.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zishi
 * 整合Redis作为session存储
 */
@SpringBootApplication
public class StompServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StompServerApplication.class, args);
    }

}
