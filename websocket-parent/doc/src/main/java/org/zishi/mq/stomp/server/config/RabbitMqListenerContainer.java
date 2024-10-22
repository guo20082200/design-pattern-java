package org.zishi.mq.stomp.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zishi.mq.stomp.server.service.ChatService;

/**
 * @author zishi
 */
@Slf4j
@Configuration
public class RabbitMqListenerContainer {

    @Autowired
    private ChatService chatService;


    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * 接受消息的监听，这个监听会接受消息队列topicQueue的消息
     * 针对消费者配置
     *
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer(Queue queue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        //设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            byte[] body = message.getBody();
            String msg = new String(body);
            log.info("rabbitmq收到消息 : {}", msg);
            Boolean sendToWebsocket = chatService.sendMsg(msg);

            if (sendToWebsocket) {
                log.info("消息处理成功！ 已经推送到websocket！");
                assert channel != null;
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true); //确认消息成功消费
            }
        });
        return container;
    }
}
