package org.zishi.mq.websocketserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import org.zishi.mq.websocketserver.dto.Greeting;
import org.zishi.mq.websocketserver.dto.HelloMessage;

import java.nio.charset.StandardCharsets;

/**
 * @author zishi
 */
@Controller
@RequestMapping
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @ResponseBody
    @GetMapping("/hello")
    public void greeting2() throws Exception {
        GenericMessage<byte[]> message = new GenericMessage<>("abcdfef".getBytes(StandardCharsets.UTF_8));
        simpMessagingTemplate.send("/topic/greetings", message);
    }


    /**
     * 1. 可以直接使用实体类来接收请求体
     * public Greeting greeting01(@RequestBody HelloMessage message) throws Exception {
     * 2. 可以使用Message对象来接收请求体, Message对象里面可以获取到MessageHeaders信息
     * public Greeting greeting(@RequestBody Message<HelloMessage> message) throws Exception
     * <p>
     * 3.MessageHeaders 可以作为参数获取（和2获取到的信息一致）
     * public Greeting greeting(@RequestBody Message<HelloMessage> message, MessageHeaders headers) throws Exception
     * <p>
     * 4. 通过 MessageHeaderAccessor, SimpMessageHeaderAccessor, and StompHeaderAccessor 获取 MessageHeaders
     *
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(@RequestBody Message<HelloMessage> message) throws Exception {
        Thread.sleep(100);
        //MessageHeaders headers = message.getHeaders();
        //System.out.println(headers);

        // 1. 通过 MessageHeaderAccessor 获取 MessageHeaders，
        /*MessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message);
        assert accessor != null;
        // accessor 可以修改 MessageHeaders对象里面的内容
        MessageHeaders messageHeaders = accessor.getMessageHeaders();
        System.out.println(messageHeaders);*/

        // 2. 通过 SimpMessageHeaderAccessor 获取 MessageHeaderAccessor
        //MessageHeaderAccessor accessor = SimpMessageHeaderAccessor.getAccessor(message);

        // 3. 通过 StompHeaderAccessor 获取 MessageHeaderAccessor
        // MessageHeaderAccessor accessor1 = StompHeaderAccessor.getAccessor(message);


        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getPayload().getName()) + "!");
    }

    /**
     * 通过 @Payload 直接指定消息体
     *
     * @param message
     * @return
     */
    @MessageMapping("/hello2")
    @SendTo("/topic/greetings")
    public Greeting greeting02(@Payload HelloMessage message, MessageHeaders headers) {

        System.out.println("message: " + message.getClass());
        System.out.println("headers: " + headers);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    /**
     * @param message       消息体
     *                      // @param simpSessionId 消息头，可以接收所有的消息头信息
     * @param simpSessionId 消息头，可以接收所有的消息头信息
     * @return
     * @Header 获取指定key 的消息头信息
     * For access to a specific header value-along with type conversion using an org.springframework.core.convert.converter.Converter, if necessary.
     * @Headers, 作用类似与 MessageHeaders
     * For access to all headers in the message. This argument must be assignable to java.util.Map.
     * public Greeting greeting03(@Payload HelloMessage message, @Headers Map<String, Object> headers)
     */
    @MessageMapping("/hello3")
    @SendTo("/topic/greetings")
    public Greeting greeting03(@Payload HelloMessage message, @Header("simpSessionId") String simpSessionId) {

        System.out.println("message: " + message.getClass());
        System.out.println("simpSessionId: " + simpSessionId);
        /*
        默认情况下，@MessageMapping所标识方法的返回值会被序列化为一个payload（通过一个匹配的MessageConverter）
        然后发送消息到brokerChannel，从brokerChannel广播消息到subscribers
        outbound message 的 destination 和 inbound message 的 destination 一样，但是前缀为 /topic
        */
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");

    }


    /**
     * @param message 消息体
     * @return 返回值
     * // @DestinationVariable 为了访问模板来从 Message 对象的destination提取出来的变量，必要的情况下值被转换成为方法的参数类型
     */
    @MessageMapping("/hello4/{destination}")
    @SendTo("/topic/greetings")
    public Greeting greeting04(@RequestBody Message<HelloMessage> message, @DestinationVariable("destination") String destination) {

        System.out.println("message: " + message.getClass());
        System.out.println("destination: " + destination);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getPayload().getName()) + "!");
    }

    /**
     * SendTo: 自定义消息的输出destination
     * 1. 这里没有写 SendTo 注解，默认内容和MessageMapping的一样，但是前缀为：/topic
     * 2. 如果使用了 SendTo注解，但是没有指定value的值，默认内容和MessageMapping的一样，但是前缀为：/topic
     * 3. 如果使用了 SendTo注解，并且指定value的值，默认值便不再生效，前缀也可以不是/topic
     * @param message
     * @return
     */
    @MessageMapping("/greetings")
    @MessageExceptionHandler
    public Greeting handle(@RequestBody Message<HelloMessage> message) {
        //return "[" + System.currentTimeMillis() + ": " + greeting;
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getPayload().getName()) + "!");
    }


    @MessageMapping("/greetings2")
    @SendTo({"/topic/abc", "/topic2/def"})
    public Greeting handle2(@RequestBody Message<HelloMessage> message) {
        //return "[" + System.currentTimeMillis() + ": " + greeting;
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getPayload().getName()) + "!");
    }

}