package org.zishi.mq.websocketserver.d03.config;

import org.zishi.mq.websocketserver.d03.WebSocketMsgVo;

/**
 * @author zishi
 */
public interface WebSocketService {

    Object sendStompMsg(WebSocketMsgVo webSocketMsgVo);

    Object sendOther();
}
