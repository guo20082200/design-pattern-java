package org.zishi.mq.stomp.server.vo;

import lombok.Data;

/**
 * @author zishi
 */
@Data
public class SendToOneReqVO {

    private String content;
    private String uid;
}
