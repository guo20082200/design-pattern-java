package org.zishi.mq.stomp.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.zishi.mq.stomp.server.interceptor.UserAuthenticationChannelInterceptor;

/**
 * @author zishi
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class RabbitMqWebsocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    private TaskScheduler messageBrokerTaskScheduler;

    @Autowired
    private RabbitMqProperties rabbitMQProperties;

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

        //registry.enableStompBrokerRelay("/queue/", "/topic/");
        //.setTcpClient(createTcpClient());

        // 发送到服务端目的地前缀
        /*registry.setApplicationDestinationPrefixes("/app")
                // 开启简单消息代理，指定消息订阅前缀
                .enableSimpleBroker("/queue/", "/topic/");*/


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

        registry.enableStompBrokerRelay("/exchange", "/queue", "/topic", "/chat")
                .setTaskScheduler(this.messageBrokerTaskScheduler)
                .setRelayHost(rabbitMQProperties.getRabbitmqHost())
                .setRelayPort(rabbitMQProperties.getRabbitmqStompPort())
                .setClientLogin(rabbitMQProperties.getRabbitmqUsername())
                .setClientPasscode(rabbitMQProperties.getRabbitmqPassword())
                .setSystemLogin(rabbitMQProperties.getRabbitmqUsername())
                .setSystemPasscode(rabbitMQProperties.getRabbitmqPassword())
                .setSystemHeartbeatSendInterval(10000 * 10000)
                .setSystemHeartbeatReceiveInterval(10000 * 10000);
        registry.setApplicationDestinationPrefixes("/app", "/com");
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
