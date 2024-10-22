package org.zishi.mq.websocketserver.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.zishi.mq.websocketserver.dto.ChatMessage;

/**
 * @author zishi
 */
@Slf4j
@Service
public class ChatService {
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;


    public Boolean sendMsg(String msg) {
        try {
            JSONObject msgJson = JSONUtil.parseObj(msg);

            log.info("msgjson:{}",msgJson);
            if ("all".equals(msgJson.getStr("to")) && msgJson.getStr("type").equals(ChatMessage.MessageType.CHAT.toString())){
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            }else if ("all".equals(msgJson.getStr("to")) && msgJson.getStr("type").equals(ChatMessage.MessageType.JOIN.toString())) {
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            }else if("all".equals(msgJson.getStr("to")) &&  msgJson.getStr("type").equals(ChatMessage.MessageType.LEAVE.toString())) {
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            }else if (!"all".equals(msgJson.getStr("to")) &&  msgJson.getStr("type").equals(ChatMessage.MessageType.CHAT.toString())){
                simpMessageSendingOperations.convertAndSendToUser(msgJson.getStr("to"),"/topic/msg", msgJson);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

}