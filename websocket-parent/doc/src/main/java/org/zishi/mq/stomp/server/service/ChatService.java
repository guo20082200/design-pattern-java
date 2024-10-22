package org.zishi.mq.stomp.server.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.zishi.mq.stomp.server.enums.MessageType;

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

            log.info("msgjson:{}", msgJson);
            JSONObject payload = msgJson.getJSONObject("payload");
            String to = payload.getStr("to");
            String type =  msgJson.getStr("type");
            simpMessageSendingOperations.convertAndSendToUser(to, "/topic/private", payload);

           /* if ("all".equals(to)) {

            } else {

            }*/
            /*if ("all".equals(msgJson.getStr("to")) && msgJson.getStr("type").equals(MessageType.CHAT.toString())) {
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            } else if ("all".equals(msgJson.getStr("to")) && msgJson.getStr("type").equals(MessageType.JOIN.toString())) {
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            } else if ("all".equals(msgJson.getStr("to")) && msgJson.getStr("type").equals(MessageType.LEAVE.toString())) {
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            } else if (!"all".equals(msgJson.getStr("to")) && msgJson.getStr("type").equals(MessageType.CHAT.toString())) {
                String to = msgJson.getStr("to");
                simpMessageSendingOperations.convertAndSendToUser(to, "/topic/private", msgJson);
            }*/
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

}