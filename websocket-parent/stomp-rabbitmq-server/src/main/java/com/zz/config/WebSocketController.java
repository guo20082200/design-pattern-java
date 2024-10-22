package com.zz.config;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

/**
 * @author zishi
 */
@Controller
public class WebSocketController {

    @MessageMapping("/msg-from-user")
    @SendToUser("/topic/msg-to-user")
    public String handle(String msg) {
        System.out.println("Received message: " + msg);
        return msg;
    }
}
