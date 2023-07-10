package com.zishi.structure.proxy.im02;

import com.zishi.structure.proxy.im01.ILawSuit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    public static Object getDynProxy(Object target) {
        InvocationHandler handler = new DynProxyLawyer(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
    }

    public static void main(String[] args) {
        ILawSuit proxy = (ILawSuit) ProxyFactory.getDynProxy(new CuiHuaNiu());
        proxy.submit("工资流水在此");
        proxy.defend();
    }
}