package doc.d03.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * websocket信息vo对象
 *
 * @author zhengwen
 **/
@Data
public class WebSocketMsgVo<T> {
    /**
     * 发送方
     */
    private String from;

    /**
     * 接收方
     */
    private String to;

    /**
     * 时间
     */
    private LocalDateTime time = LocalDateTime.now();

    /**
     * 平台来源
     */
    private String platform;
    /**
     * 主题通道
     */
    private String topicChannel;

    /**
     * 信息业务对象
     */
    private T data;
}

