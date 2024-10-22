package xxx;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.scheduling.TaskScheduler;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.session.Session;
//import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.zishi.mq.stomp.server.interceptor.AuthenticationInterceptor;

/**
 * @author zishi
 *
 * AbstractSessionWebSocketMessageBrokerConfigurer背后做了什么事情：
 * 1. WebSocketConnectHandlerDecoratorFactory 作为一个WebSocketHandlerDecoratorFactory被添加到 WebSocketTransportRegistration，
 * 这确保一个定制化的SessionConnectEvent（包含了一个WebSocketSession）is fired
 * 当 Spring Session结束的时候，那么WebSocketSession有必要结束任意的仍旧是打开的WebSocket连接
 * 2. SessionRepositoryMessageInterceptor 作为一个HandshakeInterceptor 被添加到每一个 StompWebSocketEndpointRegistration，
 * 这确保Session被添加到WebSocket的属性，用来支持更新最后的访问时间
 * 3. SessionRepositoryMessageInterceptor  作为一个 ChannelInterceptor 被添加到 inbound（输入的） ChannelRegistration，
 * 这确保每次收到inbound message（输入消息），Spring Session最后的访问时间会被更新
 * 4. WebSocketRegistryListener 作为一个bean被创建，
 * 这确保我们可以有一个映射集（所有的Session id集合和对应的WebSocket连接）
 * 通过维护这个这个映射集，当Spring Session (HttpSession) 结束的时候，我们可以关闭所有的WebSocket连接
 *
 */
//@Configuration
//@EnableScheduling
//@EnableWebSocketMessageBroker
//public class WebSocketConfigWithHttpSession extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {
//
//    private TaskScheduler messageBrokerTaskScheduler;
//
//    private AuthenticationInterceptor authenticationInterceptor;
//
//
//    @Autowired
//    public void setMessageBrokerTaskScheduler(@Lazy TaskScheduler taskScheduler, AuthenticationInterceptor authenticationInterceptor) {
//        this.authenticationInterceptor = authenticationInterceptor;
//        this.messageBrokerTaskScheduler = taskScheduler;
//    }
//
//
//    /**
//     *
//     * @param registry
//     */
//    //@Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//
//        // Enables a simple in-memory broker
//        registry.enableSimpleBroker("/topic");
//                //.setHeartbeatValue(new long[]{2000, 20000})
//                //.setHeartbeatValue(new long[] {0, 0})
//                //.setTaskScheduler(this.messageBrokerTaskScheduler);
//
//        registry.setApplicationDestinationPrefixes("/app");
//    }
//
//
//    /**
//     *  registerStompEndpoints 方法重命名为 configureStompEndpoints
//     * @param registry StompEndpointRegistry
//     */
//    @Override
//    protected void configureStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/stomp/websocketJS").withSockJS();
//    }
//
//    /*@Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/gs-guide-websocket");
//    }*/
//
//    //@Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(authenticationInterceptor);
//    }
//
//}