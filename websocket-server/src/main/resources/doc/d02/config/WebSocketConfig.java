package doc.d02.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.zishi.mq.websocketserver.d02.handler.HttpAuthHandler;
import org.zishi.mq.websocketserver.d02.interceptor.MyInterceptor;


/**
 * @author zishi
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public WebSocketConfig(HttpAuthHandler httpAuthHandler, MyInterceptor myInterceptor) {
        this.httpAuthHandler = httpAuthHandler;
        this.myInterceptor = myInterceptor;
    }

    private final HttpAuthHandler httpAuthHandler;

    private final MyInterceptor myInterceptor;

    /**
     * setAllowedOrigins("*")这个是关闭跨域校验，方便本地调试，线上推荐打开。
     * @param registry 注册
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(httpAuthHandler, "im")
                .addInterceptors(myInterceptor)
                .setAllowedOrigins("*");
    }
}