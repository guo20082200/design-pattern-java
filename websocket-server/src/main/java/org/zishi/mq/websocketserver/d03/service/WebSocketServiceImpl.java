package org.zishi.mq.websocketserver.d03.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.zishi.mq.websocketserver.d03.vo.WebSocketMsgVo;
import org.zishi.mq.websocketserver.util.JacksonUtil;

/**
 * @author zhengwen
 **/
@Slf4j
@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Object sendStompMsg(WebSocketMsgVo webSocketMsgVo) {
        String topicChannel = webSocketMsgVo.getTopicChannel();
        if (!topicChannel.isBlank()) {
            topicChannel = "/" + topicChannel;
        }
        String message = JacksonUtil.writeValueAsString(webSocketMsgVo);
        String to = webSocketMsgVo.getTo();
        try {
            if (!to.isBlank()) {
                //MD 不明原因用convertAndSendToUser不能收到，确认订阅没有问题
                //simpMessagingTemplate.convertAndSendToUser(to, topicChannel, message);
                simpMessagingTemplate.convertAndSend(topicChannel + "/" + to, message);
            } else {
                simpMessagingTemplate.convertAndSend(topicChannel, message);
            }
            return "OK";
        } catch (Exception e) {
            return "发送失败";
        }

    }

    @Override
    public Object sendOther() {

        WebSocketMsgVo<String> vo = new WebSocketMsgVo<>();
        String res = JacksonUtil.writeValueAsString(vo);
        //simpMessagingTemplate.convertAndSend("/ad/zs", res);
        simpMessagingTemplate.convertAndSend("/user/zs/ad", res);
        return "OK";
    }

    @Override
    public Object userQueueMessage() {
        WebSocketMsgVo<String> vo = new WebSocketMsgVo<>();
        String res = JacksonUtil.writeValueAsString(vo);
        //simpMessagingTemplate.convertAndSend("/user/queue/message", res);
        simpMessagingTemplate.convertAndSend("/user/zs/ad", res);
        return "OK";
    }
}

