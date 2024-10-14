package doc.d01.controller;

import jakarta.websocket.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zishi.mq.websocketserver.d01.endpoint.WsServerEndpoint;

import java.io.IOException;

/**
 * @author zishi
 */
@RestController
@RequestMapping("/hello")
public class HelloController {


    @GetMapping("/test")
    public String test() throws IOException {
        Session session = WsServerEndpoint.sessions.get("abc");
        session.getBasicRemote().sendText("abac");
        return "abf";

    }
}
