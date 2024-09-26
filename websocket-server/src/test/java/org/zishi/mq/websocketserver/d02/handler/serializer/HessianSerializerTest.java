package org.zishi.mq.websocketserver.d02.handler.serializer;

import org.junit.jupiter.api.Test;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.jetty.JettyWebSocketSession;
import org.zishi.mq.websocketserver.d02.serializer.HessianSerializer;

class HessianSerializerTest {

    @Test
    void serialize() {

        WebSocketSession webSocketSession = new JettyWebSocketSession(null);
        HessianSerializer serializer = new HessianSerializer();
        serializer.serialize(webSocketSession);
    }

    @Test
    void deserialize() {
    }
}