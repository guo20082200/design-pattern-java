package org.zishi.mq.stomp.server.controller;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.zishi.mq.stomp.server.config.RabbitConfig;
import org.zishi.mq.stomp.server.dto.ChatMessage;
import org.zishi.mq.stomp.server.dto.StompAuthenticatedUser;
import org.zishi.mq.stomp.server.vo.WebSocketMsgVO;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zishi
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class RabbitMqController {

    private final SimpUserRegistry simpUserRegistry;

    private final RabbitMessagingTemplate rabbitMessagingTemplate;


    @MessageMapping("/topic.queue")
    public ChatMessage privateChat(Message<ChatMessage> message) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        log.info("Private chat message received: {}", message);
        //Message<ChatMessage> message = new GenericMessage<>(chatMessage);
        String jsonStr = JSONUtil.toJsonStr(message);
        rabbitMessagingTemplate.convertAndSend(RabbitConfig.MQ_EXCHANGE, RabbitConfig.MSG_TOPIC_KEY, jsonStr);
        return message.getPayload();
    }

}


