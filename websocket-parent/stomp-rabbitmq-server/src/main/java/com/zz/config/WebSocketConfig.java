package com.zz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.config.StompBrokerRelayRegistration;
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec;
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.net.InetSocketAddress;


/**
 * @author zishi
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private RabbitMQProperties rabbitMqProperties;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/handshake");
    }

    public static final String[] DESTINATION_PREFIXES = {"/amq/queue", "temp-queue", "/exchange", "/queue", "/topic"};

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/send");
        registry.setUserDestinationPrefix("/user");
        StompBrokerRelayRegistration registration =
                registry.enableStompBrokerRelay(DESTINATION_PREFIXES)
                        .setSystemHeartbeatSendInterval(0L)
                        .setSystemHeartbeatReceiveInterval(0L)
                        .setRelayHost(rabbitMqProperties.getRabbitmqHost())
                        .setVirtualHost(rabbitMqProperties.getVirtualHost())
                        .setRelayPort(Integer.parseInt(rabbitMqProperties.getRabbitmqStompPort()))
                        .setClientLogin(rabbitMqProperties.getRabbitmqUsername())
                        .setClientPasscode(rabbitMqProperties.getRabbitmqPassword())
                        .setSystemLogin(rabbitMqProperties.getRabbitmqUsername())
                        .setSystemPasscode(rabbitMqProperties.getRabbitmqPassword())
                        .setTcpClient(createClient());

        //System.out.println(registration);
    }

    public ReactorNettyTcpClient<byte[]> createClient() {
        return new ReactorNettyTcpClient<>(client ->
                client.remoteAddress(() -> new InetSocketAddress("172.16.23.77", 61613)),
                new StompReactorNettyCodec());
    }
}
