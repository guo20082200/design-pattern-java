package doc.d02.client;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

/**
 * @author zishi
 */
public class MyStompSessionHandler extends StompSessionHandlerAdapter {
 
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("StompHeaders: " + connectedHeaders.toString());
        //订阅地址，发送端前面没有/user
        String destination = "/user/queue/message";
        //订阅消息
        session.subscribe(destination, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return byte[].class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                //todo 只能接收到byte[]数组，没时间研究原因
                System.out.println(new String((byte[])payload));
            }
        });
    }
}