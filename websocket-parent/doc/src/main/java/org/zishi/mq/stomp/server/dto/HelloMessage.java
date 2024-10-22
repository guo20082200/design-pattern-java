package org.zishi.mq.stomp.server.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zishi
 */
@Setter
@Getter
public class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

}