package org.example.jucdemo2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author zishi
 */
@RestController("MapHelloController")
@RequestMapping("/hello")
public class HelloController {

    private Map<String, String> stringStringMap = new HashMap<>();
    private Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();


    @GetMapping("/test")
    public Object test(@RequestParam Map<String, String> params) throws InterruptedException {
        System.out.println(params);
        TimeUnit.SECONDS.sleep(4);
        stringStringMap.putAll(params);
        System.out.println(stringStringMap);

        TimeUnit.SECONDS.sleep(3);
        String s = stringStringMap.get("1");
        System.out.println(s);
        return stringStringMap;
    }

    @GetMapping("/test2")
    public Object test2(@RequestParam Map<String, String> params) throws InterruptedException {
        System.out.println(params);
        TimeUnit.SECONDS.sleep(2);
        synchronized (this) {
            concurrentHashMap.putAll(params);
            System.out.println(concurrentHashMap);
            TimeUnit.SECONDS.sleep(3);
            String s = concurrentHashMap.get("1");
            System.out.println(s);
        }

        return concurrentHashMap;
    }
}



