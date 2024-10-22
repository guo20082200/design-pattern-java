package org.zishi.mq.stomp.server.dto;

import lombok.Getter;
import lombok.Setter;
import org.zishi.mq.stomp.server.enums.MessageType;

import java.io.Serializable;

/**
 * @author zishi
 */
@Setter
@Getter
public class ChatMessage implements Serializable {
    private MessageType type;
    private String content;
    private String sender;

    private String to;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}