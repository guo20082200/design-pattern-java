package com.zishi.qq.mvcdemo.resovler;


import org.springframework.context.ApplicationContext;

public class SpringArgumentResolver {

    public static ArgumentResolver from(ApplicationContext applicationContext) {

        return ArgumentResolver.from(type -> {
            try {
                return applicationContext.getBean(type);
            } catch (Exception e) {
                return null;
            }
        });
    }
}