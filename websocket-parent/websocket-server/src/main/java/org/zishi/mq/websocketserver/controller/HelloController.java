package org.zishi.mq.websocketserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import org.zishi.mq.websocketserver.dto.Greeting;

/**
 * @author zishi
 */
@Controller
public class HelloController {

    private SimpMessagingTemplate template;

    @Autowired
    public HelloController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @ResponseBody
    @GetMapping(path = "/greetings")
    public void greet(@RequestParam("greeting") String greeting) {
        //String text = "[" + getTimestamp() + "]:" + greeting;
        Greeting obj = new Greeting("Helloxxx, " + HtmlUtils.htmlEscape(greeting) + "!");
        this.template.convertAndSend("/topic2/def", obj);
    }
}
