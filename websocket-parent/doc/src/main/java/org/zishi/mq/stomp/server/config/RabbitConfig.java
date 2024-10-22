package org.zishi.mq.stomp.server.config;


import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zishi.mq.stomp.server.service.ChatService;

/**
 * @author zishi
 */
@Slf4j
@Configuration
public class RabbitConfig {

    /***
     * 绑定键
     */
    public static final String MSG_TOPIC_KEY = "topic.public";
    /**
     * 队列
     */
    public static final String MSG_TOPIC_QUEUE = "topic.queue";

    public static final String MQ_EXCHANGE = "topic.websocket.exchange";


    private final ChatService chatService;

    public RabbitConfig(ChatService chatService) {
        this.chatService = chatService;
    }

    @Bean
    public Queue queue() {
        return new Queue(MSG_TOPIC_QUEUE, true);
    }


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(MQ_EXCHANGE, true, false);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MSG_TOPIC_KEY);
    }



}
