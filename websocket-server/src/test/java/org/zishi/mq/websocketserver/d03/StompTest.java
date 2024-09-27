package org.zishi.mq.websocketserver.d03;

import org.junit.jupiter.api.Test;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.zishi.mq.websocketserver.d03.client.CustomStompSessionHandler;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class StompTest {


    private final StompClientSupport stompClient = new StompClientSupport() {};


    @Test
    void test() throws ExecutionException, InterruptedException, URISyntaxException, IOException {

        //WebSocketStompConfig config = new WebSocketStompConfig();

        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new WebSocketClient() {
            @Override
            public CompletableFuture<WebSocketSession> execute(WebSocketHandler webSocketHandler, String uriTemplate, Object... uriVariables) {
                return null;
            }

            @Override
            public CompletableFuture<WebSocketSession> execute(WebSocketHandler webSocketHandler, WebSocketHttpHeaders headers, URI uri) {
                return null;
            }
        });

        /*CompletableFuture<StompSession> future = webSocketStompClient.connectAsync("ws://localhost:8080/stomp/websocket", new CustomStompSessionHandler() {
        }, "token", "abc");*/

        URI uri = new URI("ws://localhost:8080/stomp/websocket");
        CompletableFuture<StompSession> future = webSocketStompClient.connectAsync(uri, new WebSocketHttpHeaders() {

        }, new StompHeaders() {

        }, new StompSessionHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return byte[].class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {

                System.out.println("StompTest.handleFrame");
            }

            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {

                System.out.println("StompTest.afterConnected");
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {

                System.out.println("StompTest.handleException");
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {

                System.out.println("StompTest.handleTransportError");
            }
        });



        webSocketStompClient.start();

        StompSession session = future.get();

        System.out.println(session.getSessionId());

        System.in.read();


    }

}
