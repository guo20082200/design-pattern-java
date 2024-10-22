package org.zishi.mq.stomp.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.zishi.mq.stomp.server.interceptor.MyInterceptor;
import org.zishi.mq.stomp.server.interceptor.UserAuthenticationChannelInterceptor;

/**
 * @author zishi
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { // <2>
        registry.addEndpoint("/websocket")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                //.addInterceptors(new MyInterceptor())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 发送到服务端目的地前缀
        registry.setApplicationDestinationPrefixes("/app")
                // 开启简单消息代理，指定消息订阅前缀
                .enableSimpleBroker("/queue/", "/topic/");
        registry.setUserDestinationPrefix("/user");
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 拦截器配置
        registration.interceptors(new UserAuthenticationChannelInterceptor());

    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        // 这里我们设置入站消息最大为8K
        registry.setMessageSizeLimit(8 * 1024);
    }

}
