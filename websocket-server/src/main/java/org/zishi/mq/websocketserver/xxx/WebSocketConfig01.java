package org.zishi.mq.websocketserver.xxx;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.standard.StandardWebSocketUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.List;

/**
 * @author zishi
 */
//@Configuration
//@EnableWebSocketMessageBroker
public class WebSocketConfig01 implements WebSocketMessageBrokerConfigurer {


    /**
     * 0. 应用启动的时候 第0个执行的方法
     * <p>
     * 配置MessageChannel 被用于从WebSocket客户端来的消息
     * 默认情况下，该channel被一个线程数是1的线程池支持
     * 推荐定制化线程池设置为生产使用
     *
     * @param registration MessageChannel 这个类的定制化配置的注册中心
     */
    @Override
    public void configureClientInboundChannel(@Nonnull ChannelRegistration registration) {
        //System.out.println("WebSocketConfig.configureClientInboundChannel");

        // 自定义线程池的使用
        //public TaskExecutorRegistration taskExecutor(@Nullable ThreadPoolTaskExecutor taskExecutor)
        WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);

        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                return ChannelInterceptor.super.preSend(message, channel);
            }

            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                ChannelInterceptor.super.postSend(message, channel, sent);
            }

            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
                ChannelInterceptor.super.afterSendCompletion(message, channel, sent, ex);
            }

            @Override
            public boolean preReceive(MessageChannel channel) {
                return ChannelInterceptor.super.preReceive(channel);
            }

            @Override
            public Message<?> postReceive(Message<?> message, MessageChannel channel) {
                return ChannelInterceptor.super.postReceive(message, channel);
            }

            @Override
            public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
                ChannelInterceptor.super.afterReceiveCompletion(message, channel, ex);
            }
        });

    }


    /**
     * 1. 应用启动的时候 第一个执行的方法
     *
     * @return Integer
     */

    @Override
    public Integer getPhase() {
        //System.out.println("WebSocketConfig.getPhase");
        return WebSocketMessageBrokerConfigurer.super.getPhase();
    }


    /**
     * 2. 应用启动的时候 第二个执行的方法
     * <p>
     * 配置  处理来自和发送到WebSocket客户端的消息 相关的 选项
     *
     * @param registry Configure the processing of messages received from and sent to WebSocket clients.
     */

    @Override
    public void configureWebSocketTransport(@Nonnull WebSocketTransportRegistration registry) {
        //System.out.println("WebSocketConfig.configureWebSocketTransport");
        //WebSocketMessageBrokerConfigurer.super.configureWebSocketTransport(registry);

        //配置输入消息最大值
        registry.setMessageSizeLimit(1024 * 1024);
        //
        registry.setTimeToFirstMessage(30000);
        // 发送消息的最大时长
        registry.setSendTimeLimit(5 * 1000);
    }


    /**
     * 3. 应用启动的时候 第三个执行的方法
     * <p>
     * 注册 STOMP 断点映射到每一个特定的URL， 启动和配置SockJS fallback选项（可选择的）
     * <p>
     * 这里可以注册多个ws的路径，
     *
     * @param registry A contract for registering STOMP over WebSocket endpoints.
     */

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //System.out.println("WebSocketConfig.registerStompEndpoints");

        // 开启 SockJS fallback 选项
        registry.addEndpoint("/zishi-im").withSockJS();

        // 不开启 SockJS
        registry.addEndpoint("/portfolio")
                .setHandshakeHandler(handshakeHandler());

        //registration.addInterceptors();
        //registration.setAllowedOriginPatterns();
        //registration.setAllowedOrigins()
        //registration.setHandshakeHandler()

        //serviceRegistration.setClientLibraryUrl()
    }


    /**
     * 4. 应用启动的时候 第四个执行的方法
     *
     * @param config
     */

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //System.out.println("WebSocketConfig.configureMessageBroker");
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");

    }


    /**
     * 5. 应用启动的时候 第五个执行的方法
     *
     * @param messageConverters the converters to configure (initially an empty list)
     * @return
     */

    @Override
    public boolean configureMessageConverters(@Nonnull List<MessageConverter> messageConverters) {
        //System.out.println("WebSocketConfig.configureMessageConverters");
        return WebSocketMessageBrokerConfigurer.super.configureMessageConverters(messageConverters);
    }


    /**
     * 6. 应用启动的时候 第六个执行的方法
     *
     * @param argumentResolvers
     * @return
     */

    @Override
    public void addArgumentResolvers(@Nonnull List<HandlerMethodArgumentResolver> argumentResolvers) {
        //System.out.println("WebSocketConfig.addArgumentResolvers");
        WebSocketMessageBrokerConfigurer.super.addArgumentResolvers(argumentResolvers);
    }


    /**
     * 7. 应用启动的时候 第7个执行的方法
     *
     * @param returnValueHandlers the handlers to register (initially an empty list)
     */

    @Override
    public void addReturnValueHandlers(@Nonnull List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        //System.out.println("WebSocketConfig.addReturnValueHandlers");
        WebSocketMessageBrokerConfigurer.super.addReturnValueHandlers(returnValueHandlers);
    }


    /**
     * 应用启动的时候不执行该方法
     *
     * @param registration
     */

    @Override
    public void configureClientOutboundChannel(@Nonnull ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientOutboundChannel(registration);
    }


    @Bean
    public DefaultHandshakeHandler handshakeHandler() {
        StandardWebSocketUpgradeStrategy strategy = new StandardWebSocketUpgradeStrategy();
        return new DefaultHandshakeHandler(strategy);
    }


}
