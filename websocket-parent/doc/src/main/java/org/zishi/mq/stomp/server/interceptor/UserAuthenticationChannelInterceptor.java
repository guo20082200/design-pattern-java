package org.zishi.mq.stomp.server.interceptor;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.zishi.mq.stomp.server.dto.StompAuthenticatedUser;

import java.security.Principal;
import java.util.Objects;

/**
 * @author zishi
 */
@Slf4j
public class UserAuthenticationChannelInterceptor implements ChannelInterceptor {

    private static final String USER_NAME = "user-name";
    private static final String USER_TOKEN = "user-token";

    @Override
    public Message<?> preSend(@Nonnull Message<?> message, @Nonnull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert accessor != null;
        MessageHeaders messageHeaders = accessor.getMessageHeaders();
        log.info("messageHeaders 为：{}", messageHeaders);

        String username = accessor.getFirstNativeHeader(USER_NAME);
        String userToken = accessor.getFirstNativeHeader(USER_TOKEN);

        Principal user = accessor.getUser();

        if(Objects.isNull(user)) {
            // TODO: 进行用户校验
            //String sessionId = accessor.getSessionId();
            accessor.setUser(new StompAuthenticatedUser(username, userToken));
        }else {
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                // TODO: 进行用户校验
                log.info("Stomp User-Related headers found, userID: {}, username:{}", username, userToken);
                String sessionId = accessor.getSessionId();
                log.info("Stomp Use sessionId :{}", sessionId);
                accessor.setUser(new StompAuthenticatedUser(username, userToken));
            }/*else if (StompCommand.CONNECTED.equals(accessor.getCommand())) {
                // TODO: 进行用户校验
                String sessionId = accessor.getSessionId();
                accessor.setUser(new StompAuthenticatedUser(sessionId, username));
            }*/
        }

        return message;
    }

}
