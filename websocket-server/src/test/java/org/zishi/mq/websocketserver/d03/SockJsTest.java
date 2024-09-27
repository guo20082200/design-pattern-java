package org.zishi.mq.websocketserver.d03;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.stomp.StompClientSupport;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SockJsTest {

    @Test
    void test() throws IOException, ExecutionException, InterruptedException {
        List<Transport> transports = new ArrayList<>(2);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockJsClient = new SockJsClient(transports);

        StompClientSupport stompClientSupport = new StompClientSupport() {
        };


        CompletableFuture<WebSocketSession> execute = sockJsClient.execute(new MyWebSocketHandler(), "ws://localhost:8080/stomp/websocket");


        WebSocketSession session = execute.get();
        String id = session.getId();


        System.in.read();
    }
}

