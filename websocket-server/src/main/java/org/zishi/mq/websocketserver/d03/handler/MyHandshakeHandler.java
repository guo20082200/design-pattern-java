package org.zishi.mq.websocketserver.d03.handler;

import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * @author zhengwen
 **/
@Slf4j
public class MyHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        //log.info("--websocket的http连接握手之后--");
        //设置认证用户
        return (Principal) attributes.get("user");
    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        super.setServletContext(servletContext);
        // servletContext.setAttribute(WebSocketHandler.class.getName(), this);
    }
}
