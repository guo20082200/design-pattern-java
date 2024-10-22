package org.zishi.mq.stomp.server.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.zishi.mq.stomp.server.service.ChatService;

/**
 * @author zishi
 */
@Getter
@Slf4j
@Component
public class RabbitMqProperties {
    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    private Integer rabbitmqPort;

    @Value("${spring.rabbitmq.stomp.port}")
    private Integer rabbitmqStompPort;


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqHost, rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        connectionFactory.setVirtualHost("ws_demo");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(Boolean.TRUE);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setExchange(RabbitConfig.MQ_EXCHANGE);
        //rabbitTemplate.setRoutingKey();
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("ConfirmCallback: 相关数据：{}", correlationData);
            log.info("ConfirmCallback: 确认情况：{}", ack);
            log.info("ConfirmCallback: 原因：{}", cause);
        });

        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.info("ReturnCallback:  消息：{}", returnedMessage.getMessage());
            log.info("ReturnCallback:  回应码：{}", returnedMessage.getReplyCode());
            log.info("ReturnCallback:  回应信息：{}", returnedMessage.getReplyText());
            log.info("ReturnCallback:  交换机：{}", returnedMessage.getExchange());
            log.info("ReturnCallback:  路由键：{}", returnedMessage.getRoutingKey());
        });
        return rabbitTemplate;
    }
}
