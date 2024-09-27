package org.zishi.mq.stompclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.StringMessageConverter;
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

/**
 * @author zishi
 */
@Configuration
public class MyConfig {

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
    public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient, StompSessionHandler stompSessionHandler) {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);
        webSocketStompClient.setMessageConverter(new StringMessageConverter());
        webSocketStompClient.connect("http://localhost:8080/stomp/websocketJS?token=abc", stompSessionHandler);
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
