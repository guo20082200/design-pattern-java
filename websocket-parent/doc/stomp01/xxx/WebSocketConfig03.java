package xxx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.zishi.mq.stomp.server.interceptor.AuthenticationInterceptor;

/**
 * @author zishi
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig03 implements WebSocketMessageBrokerConfigurer {

    private TaskScheduler messageBrokerTaskScheduler;

    private AuthenticationInterceptor authenticationInterceptor;


    @Autowired
    public void setMessageBrokerTaskScheduler(@Lazy TaskScheduler taskScheduler, AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.messageBrokerTaskScheduler = taskScheduler;
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/topic")
                .setHeartbeatValue(new long[]{2000, 20000})
                //.setHeartbeatValue(new long[] {0, 0})
                .setTaskScheduler(this.messageBrokerTaskScheduler);



        // 消息顺序性的保证
        //registry.setPreservePublishOrder(true);
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket")
                //.setAllowedOrigins("*")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // @SendToUser 表示要将消息发送给指定的用户，会自动在消息目的地前补上"/user"前缀。
        // 如下，最后消息会被发布在  /user/queue/notifications-username。但是问题来了，
        // 这个username是怎么来的呢？就是通过 principal 参数来获得的。
        // 那么，principal 参数又是怎么来的呢？需要在spring-websocket 的配置类中重写 configureClientInboundChannel 方法，添加上用户的认证。
        registration.interceptors(authenticationInterceptor);
    }

}