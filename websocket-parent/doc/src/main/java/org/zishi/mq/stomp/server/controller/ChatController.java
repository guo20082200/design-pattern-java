package org.zishi.mq.stomp.server.controller;

import jakarta.annotation.Resource;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.zishi.mq.stomp.server.config.RabbitConfig;
import org.zishi.mq.stomp.server.dto.ChatMessage;
import org.zishi.mq.stomp.server.dto.StompAuthenticatedUser;
import org.zishi.mq.stomp.server.vo.WebSocketMsgVO;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zishi
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    @Resource
    RabbitConfig rabbitConfig;

    private final SimpUserRegistry simpUserRegistry;
    //private final SimpMessagingTemplate simpMessagingTemplate;
    //private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final RabbitMessagingTemplate rabbitMessagingTemplate;

    @GetMapping("/page/chat")
    public ModelAndView turnToChatPage() {
        return new ModelAndView("chat");
    }

    /**
     * 群聊消息处理
     * 这里我们通过@SendTo注解指定消息目的地为"/topic/chat/group"，如果不加该注解则会自动发送到"/topic" + "/chat/group"
     *
     * @param webSocketMsgDTO 请求参数，消息处理器会自动将JSON字符串转换为对象
     * @return 消息内容，方法返回值将会广播给所有订阅"/topic/chat/group"的客户端
     */
    /*@MessageMapping("/topic/chat/group")
    @SendTo("/topic/chat/group")
    public WebSocketMsgVO groupChat(WebSocketMsgDTO webSocketMsgDTO) {
        log.info("Group chat message received: {}", webSocketMsgDTO);
        String content = String.format("来自[%s]的群聊消息: %s", webSocketMsgDTO.getName(), webSocketMsgDTO.getContent());
        return WebSocketMsgVO.builder().content(content).build();
    }*/

    /**
     * 私聊消息处理
     * 这里我们通过@SendToUser注解指定消息目的地为"/topic/chat/private"，发送目的地默认会拼接上"/user/"前缀
     * 实际发送目的地为"/user/topic/chat/private"
     *
     * @param message 请求参数，消息处理器会自动将JSON字符串转换为对象
     * @return 消息内容，方法返回值将会基于SessionID单播给指定用户
     */
    //@MessageMapping("/topic/private{sessionId}")
    //@SendToUser("/topic/private")
    //public ChatMessage privateChat(Message<ChatMessage> message, @DestinationVariable("sessionId") String sessionId) {

        //StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);


        //String sessionId = accessor.getSessionId();
       // System.out.println(sessionId);

        /*SimpUser simpUser = simpUserRegistry.getUser(principal.getName());

        String name = simpUser.getName();

        SimpSession session = simpUser.getSession(name);


        System.out.println(principal.getName());
        System.out.println(session.getId());
*/
       // log.info("Private chat message received: {}", message);
        //String content = "私聊消息回复：" + webSocketMsgDTO.getContent();
        //String username = webSocketMsgDTO.getName();

        //WebSocketMsgVO webSocketMsgVO = WebSocketMsgVO.builder().content(chatMessage.getContent()).build();
        //simpMessageSendingOperations.convertAndSendToUser("jack", "/topic/private", webSocketMsgVO);

        //String jsonStr = JSONUtil.toJsonStr(chatMessage);
        //rabbitMessagingTemplate.convertAndSend(RabbitConfig.MQ_EXCHANGE, RabbitConfig.MSG_TOPIC_KEY, jsonStr);


        //Message<ChatMessage> message = new GenericMessage<>(chatMessage);

        //RabbitTemplate rabbitTemplate = rabbitMessagingTemplate.getRabbitTemplate();
        //String exchange = rabbitTemplate.getExchange();
        //System.out.println(exchange);

        //rabbitMessagingTemplate.send("/user/topic/private", message);


       //rabbitMessagingTemplate.send("topic.queue", message);
        //privateChat2(chatMessage);
       // return message.getPayload();
   // }



    /**
     * 定时消息推送，这里我们会列举所有在线的用户，然后单播给指定用户。
     * 通过SimpMessagingTemplate实例可以在任何地方推送消息。
     */
    @Scheduled(fixedRate = 10 * 1000)
    public void pushMessageAtFixedRate() {

        log.info("当前在线人数: {}", simpUserRegistry.getUserCount());
        if (simpUserRegistry.getUserCount() <= 0) {
            return;
        }

        // 这里的Principal为StompAuthenticatedUser实例
        Set<StompAuthenticatedUser> users = simpUserRegistry.getUsers().stream()
                .map(simpUser -> StompAuthenticatedUser.class.cast(simpUser.getPrincipal()))
                .collect(Collectors.toSet());

        users.forEach(authenticatedUser -> {
            String username = authenticatedUser.getUsername();
            String userToken = authenticatedUser.getUserToken();
            WebSocketMsgVO webSocketMsgVO = new WebSocketMsgVO();
            webSocketMsgVO.setContent(String.format("定时推送的私聊消息, 接收人: %s, 时间: %s", userToken, LocalDateTime.now()));

            log.info("开始推送消息给指定用户, username: {}, 消息内容:{}", username, webSocketMsgVO);
            //simpMessagingTemplate.convertAndSendToUser(username, "/topic/chat/push", webSocketMsgVO);
        });
    }

}


