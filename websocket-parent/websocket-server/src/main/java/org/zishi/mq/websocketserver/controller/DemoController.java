package org.zishi.mq.websocketserver.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zishi.mq.websocketserver.config.RabbitConfig;
import org.zishi.mq.websocketserver.dto.ChatMessage;
import org.zishi.mq.websocketserver.vo.SendToOneReqVO;

import java.security.Principal;

/**
 * @author zishi
 */
@Slf4j
@RestController
public class DemoController {

    private final RabbitTemplate rabbitTemplate;

    public DemoController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/sendToOne")
    public void sendToOne(@RequestBody SendToOneReqVO reqVO) {
        ChatMessage message = new ChatMessage();
        message.setType(ChatMessage.MessageType.CHAT);
        message.setContent(reqVO.getContent());
        message.setTo(reqVO.getUid());
        message.setSender("系统消息");
        this.sendMessage(message);
    }

    /**
     * 接收 客户端传过来的消息 通过setSender和type 来判别时单发还是群发
     *
     * @param chatMessage
     * @param principal
     */
    @MessageMapping("/chat.sendMessageTest")
    public void sendMessageTest(@Payload ChatMessage chatMessage, Principal principal) {
        try {

            String name = principal.getName();
            chatMessage.setSender(name);
            this.sendMessage(chatMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * 接收 客户端传过来的消息 上线消息
     *
     * @param chatMessage
     */
    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage) {

        log.info("有用户加入到了websocket 消息室:{}", chatMessage.getSender());
        try {
            chatMessage.setType(ChatMessage.MessageType.JOIN);
            log.info(chatMessage.toString());
            chatMessage.setTo("all");
            this.sendMessage(chatMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * 发送消息
     *
     * @param message
     */
    private void sendMessage(ChatMessage message) {
        JSON json = JSONUtil.parse(message);
        log.info("json:{}", json.toString());
        rabbitTemplate.convertAndSend(RabbitConfig.MQ_EXCHANGE, RabbitConfig.MSG_TOPIC_KEY, json.toString());
    }
}