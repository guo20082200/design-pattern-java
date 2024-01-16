package com.zx.provider;

/**
 * xml方式服务提供者实现类
 */
public class ProviderServiceImpl implements ProviderService{

    public String sayHello(String word) {
        return word;
    }
}