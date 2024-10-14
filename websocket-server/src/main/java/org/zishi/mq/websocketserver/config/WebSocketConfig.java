package org.zishi.mq.websocketserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.zishi.mq.websocketserver.interceptor.AuthenticationInterceptor;

/**
 * @author zishi
 */
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

    private TaskScheduler messageBrokerTaskScheduler;

    private AuthenticationInterceptor authenticationInterceptor;


    @Autowired
    public void setMessageBrokerTaskScheduler(@Lazy TaskScheduler taskScheduler, AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.messageBrokerTaskScheduler = taskScheduler;
    }


    /**
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // Enables a simple in-memory broker
        registry.enableSimpleBroker("/topic")
                .setHeartbeatValue(new long[]{2000, 20000})
                //.setHeartbeatValue(new long[] {0, 0})
                .setTaskScheduler(this.messageBrokerTaskScheduler);

        registry.setApplicationDestinationPrefixes("/app");
    }


    /**
     *  registerStompEndpoints 方法重命名为 configureStompEndpoints
     * @param registry StompEndpointRegistry
     */
    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket");
    }

    /*@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket");
    }*/

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authenticationInterceptor);
    }

}