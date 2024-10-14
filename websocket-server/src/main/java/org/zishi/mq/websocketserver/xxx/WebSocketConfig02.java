package org.zishi.mq.websocketserver.xxx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.zishi.mq.websocketserver.interceptor.AuthenticationInterceptor;

/**
 * @author zishi
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig02 implements WebSocketMessageBrokerConfigurer {

    private TaskScheduler messageBrokerTaskScheduler;

    private AuthenticationInterceptor authenticationInterceptor;


    @Autowired
    public void setMessageBrokerTaskScheduler(@Lazy TaskScheduler taskScheduler, AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.messageBrokerTaskScheduler = taskScheduler;
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // Enables a simple in-memory broker
        registry.enableSimpleBroker("/topic")
                .setHeartbeatValue(new long[]{2000, 20000})
                //.setHeartbeatValue(new long[] {0, 0})
                .setTaskScheduler(this.messageBrokerTaskScheduler);


        //registry.enableSimpleBroker("/topic");

        // StompBroker, 集成RabbitMQ
        /*registry.enableStompBrokerRelay("/topic")
                .setSystemHeartbeatSendInterval(2000)
                .setTaskScheduler(this.messageBrokerTaskScheduler)
                .setAutoStartup(true)
                .setRelayHost("192.168.92.129")
                .setRelayPort(61613)
                .setVirtualHost("ws_demo")
                .setClientLogin("admin")
                .setClientPasscode("123456")
                .setSystemLogin("admin")
                .setSystemPasscode("123456")
                .setSystemHeartbeatReceiveInterval(5000)
                .setSystemHeartbeatSendInterval(4000);*/

        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket");
        // .withSockJS();
        //.setAllowedOrigins("*")
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authenticationInterceptor);
    }

}