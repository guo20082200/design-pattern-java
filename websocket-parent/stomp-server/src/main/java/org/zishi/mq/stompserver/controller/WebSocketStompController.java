package org.zishi.mq.stompserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * websocket stomp协议controller
 *
 * @author zhengwen
 **/
@RestController
@RequestMapping("/web/socket/stomp")
public class WebSocketStompController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 发送信息 stomp
     *
     * @param webSocketMsgVo 信息对象vo
     * @return 统一出参
     */
    @PostMapping("/sendStompMsg")
    @MessageMapping("/topic/greeting")
    public Object sendStompMsg() {

        try {
            //MD 不明原因用convertAndSendToUser不能收到，确认订阅没有问题
            simpMessagingTemplate.convertAndSend("/topic/greeting", "aerfaer");
            return "OK";
        } catch (Exception e) {
            return "发送失败";
        }
    }

}

