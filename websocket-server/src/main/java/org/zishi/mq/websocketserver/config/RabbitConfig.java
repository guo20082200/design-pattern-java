package org.zishi.mq.websocketserver.config;


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
import org.zishi.mq.websocketserver.service.ChatService;

/**
 * @author zishi
 */
@Slf4j
//@Configuration
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

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost", 5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("ws_demo");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(Boolean.TRUE);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("ConfirmCallback: 相关数据：{}", correlationData);
            log.info("ConfirmCallback: 确认情况：{}", ack);
            log.info("ConfirmCallback: 原因：{}", cause);
        });

        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {

            @Override
            public void returnedMessage(@Nullable ReturnedMessage returnedMessage) {
                assert returnedMessage != null;
                log.info("ReturnCallback:  消息：{}", returnedMessage.getMessage());
                log.info("ReturnCallback:  回应码：{}", returnedMessage.getReplyCode());
                log.info("ReturnCallback:  回应信息：{}", returnedMessage.getReplyText());
                log.info("ReturnCallback:  交换机：{}", returnedMessage.getExchange());
                log.info("ReturnCallback:  路由键：{}", returnedMessage.getRoutingKey());
            }
        });
        return rabbitTemplate;
    }


    /**
     * 接受消息的监听，这个监听会接受消息队列topicQueue的消息
     * 针对消费者配置
     *
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer(Queue queue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(queue);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        //设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {

            @Override
            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                byte[] body = message.getBody();
                String msg = new String(body);
                log.info("rabbitmq收到消息 : " + msg);
                Boolean sendToWebsocket = chatService.sendMsg(msg);

                if (sendToWebsocket) {
                    log.info("消息处理成功！ 已经推送到websocket！");
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), true); //确认消息成功消费

                }
            }

        });
        return container;
    }

}