package com.zishi.pattern.creational.factory.im01;

public class PhoneFactory {
    public Phone create(String type) {
        if (type.equals("IPhone")) {
            return new IPhone();
        } else if (type.equals("MPhone")) {
            return new MPhone();
        } else
            return null;
    }
}
