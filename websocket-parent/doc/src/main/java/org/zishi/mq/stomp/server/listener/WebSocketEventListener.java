package org.zishi.mq.stomp.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;
import org.zishi.mq.stomp.server.dto.ChatMessage;
import org.zishi.mq.stomp.server.enums.MessageType;


/**
 * @author zishi
 */
@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        logger.info("SessionConnectEvent a new web socket connection:{}", event.toString());
    }


    /**
     * BrokerAvailabilityEvent
     *
     * @param event
     */
    @EventListener
    public void handleBrokerAvailabilityListener(BrokerAvailabilityEvent event) {
        logger.info("handleBrokerAvailabilityListener:{}", event.toString());
    }

    /***
     * 监听消息
     * @param event
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection:{}", event.toString());
    }


    /***
     * 订阅消息
     * @param event
     */
    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        logger.info("WebSocket Subscribe:{},user:{}", event.getMessage(), event.getUser());
    }

    /***
     * 取消订阅消息
     * @param event
     */
    @EventListener
    public void handleWebSocketUnSubscribeListener(SessionUnsubscribeEvent event) {
        logger.info("WebSocket UnSubscribe:{},user:{}", event.getMessage(), event.getUser());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        /*String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            logger.info("User Disconnected : {}", username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(MessageType.LEAVE);
            chatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }*/
    }
}