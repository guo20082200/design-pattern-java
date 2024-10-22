package org.zishi.mq.stompclient.handler;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;


/**
 * @author zishi
 */
public class ClientStompSessionHandler extends StompSessionHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ClientStompSessionHandler.class);

    /**
     * 看 afterConnected方法，这个方法的调用在客户端连接完成之后：
     *
     * @param session          stomp会话
     * @param connectedHeaders 连接头信息
     */
    @Override
    public void afterConnected(StompSession session, @Nonnull StompHeaders connectedHeaders) {
        //这里需要自己写逻辑，这里只是简单的演示
        //session.acknowledge()
        logger.info("客户端已连接： headers {}", connectedHeaders);
        //session.subscribe("/topic2/def", this);
        session.subscribe("/topic2/def", new StompFrameHandler() {
            @Override
            @Nonnull
            public Type getPayloadType(@Nonnull StompHeaders headers) {
                return byte[].class;
            }
            @Override
            public void handleFrame(@Nonnull StompHeaders headers, Object payload) {
                //todo 只能接收到byte[]数组，没时间研究原因
                System.out.println(new String((byte[])payload));
            }
        });
        String message = "hello 3333333333333333333333333";
        logger.info("客户端发送：{}", message);
        session.send("/app/greeting2", message);
    }

    /**
     * 对帧的处理
     *
     * @param headers 连接头信息
     * @param payload 消息
     */
    @Override
    public void handleFrame(@Nullable StompHeaders headers, Object payload) {
        //这里需要自己写逻辑，这里只是简单的演示
        logger.info("客户端收到消息：{}", payload);
    }

    /**
     * 客户端异常处理：
     *
     * @param session   stomp会话
     * @param command   执行的命令
     * @param headers   连接头信息
     * @param payload   消息
     * @param exception 异常
     */
    @Override
    public void handleException(@Nullable StompSession session, StompCommand command,
                                @Nullable StompHeaders headers, @Nullable byte[] payload, Throwable exception) {
        //这里需要自己写逻辑，这里只是简单的演示
        logger.error("客户端错误: 异常 {}, command {}, payload {}, headers {}", exception.getMessage(), command, payload, headers);
    }

    /**
     * 传输异常处理：
     *
     * @param session   stomp会话
     * @param exception 异常
     */
    @Override
    public void handleTransportError(@Nullable StompSession session, Throwable exception) {
        //这里需要自己写逻辑，这里只是简单的演示
        logger.error("客户端传输错误：错误 {}", exception.getMessage());
    }
}
