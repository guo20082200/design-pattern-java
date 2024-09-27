package doc.d02.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.zishi.mq.websocketserver.d02.handler.WsSessionManager;
import org.zishi.mq.websocketserver.d02.serializer.HessianSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

/**
 * @author zishi
 */
@RestController
@RequestMapping("/spring")
public class SpringController {


    @GetMapping("/test")
    public String test() {
        WebSocketSession webSocketSession = WsSessionManager.get("12345");
        /*String string = JacksonUtil.writeValueAsString(webSocketSession);
        System.out.println(string);*/

        HessianSerializer serializer = new HessianSerializer();
        byte[] serialize = serializer.serialize(webSocketSession);
        System.out.println(serialize.length);


        IntStream.range(1, 10).forEach(x -> {
            try {
                webSocketSession.sendMessage(new TextMessage(x + " server 发送给 xxxx : " + LocalDateTime.now()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return "abf";

    }
}
