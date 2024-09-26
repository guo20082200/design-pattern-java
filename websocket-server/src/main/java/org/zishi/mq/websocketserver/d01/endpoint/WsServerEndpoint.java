package org.zishi.mq.websocketserver.d01.endpoint;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author buhao
 * @version WsServerEndpoint.java, v 0.1 2019-10-18 16:06 buhao
 */
@ServerEndpoint("/im")
@Component
public class WsServerEndpoint {

    public static Map<String, Session> sessions = new ConcurrentHashMap<>();

    /**
     * 连接成功
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.hashCode());
        sessions.put("abc", session);
        System.out.println("连接成功");
    }

    /**
     * 连接关闭
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println(session.hashCode());
        System.out.println("连接关闭");
    }

    /**
     * 接收到消息
     *
     * @param text
     */
    @OnMessage
    public String onMsg(String text, Session session) throws IOException {
        System.out.println(session.hashCode());
        return "servet 发送：" + text;
    }
}