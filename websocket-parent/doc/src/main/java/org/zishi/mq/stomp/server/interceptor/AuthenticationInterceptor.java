package org.zishi.mq.stomp.server.interceptor;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.zishi.mq.stomp.server.dto.UserPrincipal;

import java.util.List;

/**
 * @author zishi
 */
@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class AuthenticationInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(@Nonnull Message<?> message, @Nonnull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert accessor != null;

        MessageHeaders messageHeaders = accessor.getMessageHeaders();
        SimpMessageType simpMessageType = (SimpMessageType) messageHeaders.get("simpMessageType");
        assert simpMessageType != null;
        /*
         * 不处理心跳逻辑，处理 CONNECT 事件
         *
         * TODO： 这里的连接方法在初始化的时候调用了两次？还不清楚为什么
         */
       // if(!simpMessageType.equals(SimpMessageType.HEARTBEAT) && StompCommand.CONNECT.equals(accessor.getCommand())) {
        if(StompCommand.CONNECT.equals(accessor.getCommand())) {
            //TODO： 处理认证的逻辑、
            LinkedMultiValueMap nativeHeaders = (LinkedMultiValueMap) messageHeaders.get("nativeHeaders");
            List login = nativeHeaders.get("login");
            List passcode = nativeHeaders.get("passcode");
            List token = nativeHeaders.get("token");
            System.out.println("校验token");
            Object o = login.get(0);
            accessor.setUser(new UserPrincipal(String.valueOf(o)));
        }
        return message;
    }

}
