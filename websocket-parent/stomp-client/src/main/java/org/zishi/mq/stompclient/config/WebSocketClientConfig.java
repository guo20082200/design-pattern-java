package org.zishi.mq.stompclient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.zishi.mq.stompclient.handler.ClientStompSessionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * @author zishi
 */
@Configuration
public class WebSocketClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketClientConfig.class);
    private static final Map<String, StompSession> SESSION_MAP = new ConcurrentHashMap<>();

    public StompSession getSessionById(String sessionId) {
        return SESSION_MAP.get(sessionId);
    }

    /**
     * 基本的websocket客户端配置：
     *
     * @return WebSocketClient
     */
    @Bean
    public WebSocketClient webSocketClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        return new SockJsClient(transports);
    }


    /**
     * Stomp客户端配置：
     *
     * @param webSocketClient     webSocketClient
     * @param stompSessionHandler stomp会话处理器
     * @return WebSocketStompClient
     */
    @Bean
    public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient, StompSessionHandler stompSessionHandler) throws ExecutionException, InterruptedException {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);
        webSocketStompClient.setMessageConverter(new StringMessageConverter());
        //webSocketStompClient.setMessageConverter(new ByteArrayMessageConverter());
        CompletableFuture<StompSession> completableFuture = webSocketStompClient.connectAsync("http://localhost:8081/stomp/websocketJS?username=zhangsan&token=abc", stompSessionHandler);

        StompSession session = completableFuture.get();
        logger.info("WebSocketStompClient获取到session的ID为：{}", session.getSessionId());
        // 将session保存到redis
        SESSION_MAP.put(session.getSessionId(), session);

        //WebSocketClient webSocketClient1 = webSocketStompClient.getWebSocketClient();
        //webSocketClient1.
        //session.send()


        return webSocketStompClient;
    }

    /**
     * 会话处理配置：
     *
     * @return
     */
    @Bean
    public StompSessionHandler stompSessionHandler() {
        return new ClientStompSessionHandler();
    }
}
