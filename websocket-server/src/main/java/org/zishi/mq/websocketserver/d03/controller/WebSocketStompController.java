package org.zishi.mq.websocketserver.d03.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zishi.mq.websocketserver.d03.vo.WebSocketMsgVo;
import org.zishi.mq.websocketserver.d03.service.WebSocketService;

/**
 * websocket stomp协议controller
 *
 * @author zhengwen
 **/
@Slf4j
@RestController
@MessageMapping("/web/socket/stomp")
public class WebSocketStompController {

    @Autowired
    private WebSocketService webSocketService;

    /**
     * 发送信息 stomp
     *
     * @param webSocketMsgVo 信息对象vo
     * @return 统一出参
     */
    //@PostMapping("/sendStompMsg")
    @MessageMapping("/sendStompMsg")
    @SendTo("/user/zs/ad")
    public Object sendStompMsg(@RequestBody WebSocketMsgVo<?> webSocketMsgVo) {
        log.info("--发送信息--");
        return webSocketService.sendStompMsg(webSocketMsgVo);
    }


    @PostMapping("/other")
    public Object other() {
        log.info("--发送other信息--");
        return webSocketService.sendOther();
    }


    //@PostMapping("/user/queue/message")
    @MessageMapping("/user/queue/message")
    @SendTo("/user/zs/ad")
    public Object userQueueMessage() {
        log.info("--发送userQueueMessage信息--");
        return webSocketService.userQueueMessage();
    }


}

