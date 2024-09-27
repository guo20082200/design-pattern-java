package doc.d03.service;

import org.zishi.mq.websocketserver.d03.vo.WebSocketMsgVo;

/**
 * @author zishi
 */
public interface WebSocketService {

    Object sendStompMsg(WebSocketMsgVo webSocketMsgVo);

    Object sendOther();

    Object userQueueMessage();
}
