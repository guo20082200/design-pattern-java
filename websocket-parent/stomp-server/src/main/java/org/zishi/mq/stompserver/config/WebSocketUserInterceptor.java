package org.zishi.mq.stompserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author zishi
 */
@Configuration
public class WebSocketUserInterceptor implements ChannelInterceptor {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void afterSendCompletion(@Nullable Message<?> message, @Nullable MessageChannel channel, boolean sent, @Nullable Exception ex) {

        System.out.println("WebSocketUserInterceptor.afterSendCompletion");

        assert message != null;
        assert channel != null;


        MessageHeaders headers = message.getHeaders();
        Object simpMessageType = headers.get("simpMessageType");
        if (SimpMessageType.CONNECT.equals(simpMessageType)) {

            System.out.println("连接成功");
            String simpDestination = (String) headers.get("simpDestination");
            System.out.println(simpDestination);
        } else if (SimpMessageType.SUBSCRIBE.equals(simpMessageType)) {
            System.out.println("订阅");
            String simpDestination = (String) headers.get("simpDestination");
            System.out.println(simpDestination);

        } else if (SimpMessageType.MESSAGE.equals(simpMessageType)) {
            System.out.println("发送消息");

            String simpDestination = (String) headers.get("simpDestination");
            System.out.println(simpDestination);
            GenericMessage<String> message1 = new GenericMessage<>("OK", headers);
            System.out.println(simpMessagingTemplate);
            //channel.send(message1); 这里还是往服务端发送消息
            //simpMessagingTemplate.send("/topic/greeting", message1);
        } else if (SimpMessageType.DISCONNECT.equals(simpMessageType)) {
            System.out.println("断开连接");
            String simpDestination = (String) headers.get("simpDestination");
            System.out.println(simpDestination);
        }

    }


    @Override
    public Message<?> preSend(@Nullable Message<?> message, @Nullable MessageChannel channel) {
        MessageHeaders headers = message.getHeaders();
        Object simpMessageType = headers.get("simpMessageType");
        System.out.println(simpMessageType);
        System.out.println("--websocket信息发送前--");
        return message;
    }
}

