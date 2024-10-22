package org.zishi.mq.stompserver.mq;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class CachingConnectionFactoryTest {

    static CachingConnectionFactory factory;
    static RabbitTemplate rabbitTemplate;
    static RabbitAdmin rabbitAdmin;

    @BeforeAll
    static void setUp() {
        factory = new CachingConnectionFactory("192.168.92.129");
        System.out.println(factory);
        rabbitTemplate = new RabbitTemplate(factory);
        System.out.println(rabbitTemplate);
        rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        System.out.println(rabbitAdmin);
    }

    @Test
    void testCachingConnectionFactory() {
        //rabbitTemplate.

    }
}
