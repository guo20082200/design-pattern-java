package org.zishi.mq.stomp.server.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author zishi
 */
public class MyInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        /*System.out.println(request.getClass());
        System.out.println(response.getClass());
        System.out.println(wsHandler.getClass());
        System.out.println(attributes.getClass());


        System.out.println("MyInterceptor.beforeHandshake");*/
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {

        System.out.println("MyInterceptor.afterHandshake");
    }
}
