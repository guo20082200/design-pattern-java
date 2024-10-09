package org.zishi.mq.stompclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zishi.mq.stompclient.config.WebSocketClientConfig;

/**
 * @author zishi
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    WebSocketClientConfig webSocketClientConfig;

    @GetMapping("/sendMsg")
    public Object sendMsg(@RequestParam("sessionId") String sessionId) {
        StompSession session = webSocketClientConfig.getSessionById(sessionId);
        session.send("/ad/zs", "hello world");
        return "ok";
    }

}
