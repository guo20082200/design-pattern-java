package doc.d02.client;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;


import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author zishi
 */
public class StompClient {

    public static void main2(String[] args) throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient transport = new SockJsClient(transports);
        transport.setMessageCodec(new Jackson2SockJsMessageCodec());
        WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        //接收大小限制
        stompClient.setInboundMessageSizeLimit(1024 * 1024);
        //处理心跳
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.afterPropertiesSet();
        //for heartbeats
        stompClient.setTaskScheduler(taskScheduler);
        StompSessionHandler customHandler = new CustomStompSessionHandler();
        //可以发送请求头
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("Authorization", "admin");
        //URI uri = URI.create("http://localhost:8080/demo/webSocketServer");
        URI uri = URI.create("http://localhost:8080/stomp/websocketJS?token=23232&t=22222");
        CompletableFuture<StompSession> future = stompClient.connectAsync(uri, new WebSocketHttpHeaders(), stompHeaders, customHandler);


        //阻塞
        try {

            StompSession session = future.get();




            System.out.println(session.getSessionId());

            GenericMessage payload = new GenericMessage("aerfaerf".getBytes(StandardCharsets.UTF_8));
            StompSession.Receiptable send = session.send("/user/erfae", payload);
            System.out.println(send.getReceiptId());
            latch.await(31536000, TimeUnit.SECONDS);
            //latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        TaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());
        // for heartbeats
        stompClient.setTaskScheduler(taskScheduler);

        String url = "ws://127.0.0.1:8080/im?token=abc";
        //String url = "ws://localhost:8080/stomp/websocketJS/504/gxcn2cjq/websocket?token=zw";
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        //stompClient.connectAsync(url, sessionHandler);

        stompClient.start();
        CompletableFuture<StompSession> future = stompClient.connectAsync(url, sessionHandler);



        try {

            StompSession session = future.get();
            System.out.println(session.getSessionId());

            latch.await(31536000, TimeUnit.SECONDS);
            //latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void  test() {
        WebSocketClient client = new WebSocketClient() {
            @Override
            public CompletableFuture<WebSocketSession> execute(WebSocketHandler webSocketHandler, String uriTemplate, Object... uriVariables) {
                return null;
            }

            @Override
            public CompletableFuture<WebSocketSession> execute(WebSocketHandler webSocketHandler, WebSocketHttpHeaders headers, URI uri) {
                return null;
            }
        };
    }
}
