package org.zishi.mq.stomp.server.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.zishi.mq.stomp.server.dto.UserPrincipal;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author zishi
 */
@Slf4j
@Component
public class HeaderParamInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (raw instanceof Map) {
                //取出客户端携带的参数
                Object name = ((Map) raw).get("username");
                log.info("name:{}", name);
                if (name instanceof ArrayList) {
                    // 设置当前访问的认证用户
                    accessor.setUser(new UserPrincipal(((ArrayList) name).get(0).toString()));
                }
            }
        }
        return message;
    }
}