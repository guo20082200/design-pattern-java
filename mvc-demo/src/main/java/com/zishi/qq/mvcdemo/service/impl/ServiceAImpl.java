package com.zishi.qq.mvcdemo.service.impl;

import com.zishi.qq.mvcdemo.holder.ApplicationContextHolder;
import com.zishi.qq.mvcdemo.resovler.ArgumentResolver;
import com.zishi.qq.mvcdemo.resovler.SpringArgumentResolver;
import com.zishi.qq.mvcdemo.service.ServiceA;
import com.zishi.qq.mvcdemo.service.ServiceB;
import com.zishi.qq.mvcdemo.service.ServiceC;
import org.springframework.stereotype.Service;

@Service
public class ServiceAImpl implements ServiceA {

    private final ArgumentResolver argumentResolver;

    public ServiceAImpl() {
        this.argumentResolver = SpringArgumentResolver.from(ApplicationContextHolder.getContext());
    }

    /*private final ServiceB serviceB;
    private final ServiceC serviceC;

    public ServiceAImpl(ServiceB serviceB, ServiceC serviceC) {
        this.serviceB = serviceB;
        this.serviceC = serviceC;
    }*/

    @Override
    public void run() {
        ServiceB serviceB = argumentResolver.resolve(ServiceB.class);
        ServiceC serviceC = argumentResolver.resolve(ServiceC.class);
        System.out.println("ServiceAImpl.run" + serviceB + "........." + serviceC);
    }
}
