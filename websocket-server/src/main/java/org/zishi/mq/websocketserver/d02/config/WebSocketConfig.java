package org.zishi.mq.websocketserver.d02.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.zishi.mq.websocketserver.d02.handler.HttpAuthHandler;
import org.zishi.mq.websocketserver.d02.interceptor.MyInterceptor;


/**
 * @author zishi
 */
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    @Autowired
//    private HttpAuthHandler httpAuthHandler;
//    @Autowired
//    private MyInterceptor myInterceptor;
//
//    /**
//     * setAllowedOrigins("*")这个是关闭跨域校验，方便本地调试，线上推荐打开。
//     * @param registry
//     */
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(httpAuthHandler, "im")
//                .addInterceptors(myInterceptor)
//                .setAllowedOrigins("*");
//    }
//}