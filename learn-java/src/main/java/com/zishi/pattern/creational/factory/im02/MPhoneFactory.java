package com.zishi.pattern.creational.factory.im02;

import com.zishi.pattern.creational.factory.im01.MPhone;
import com.zishi.pattern.creational.factory.im01.Phone;

public class MPhoneFactory implements PhoneFactory{
    @Override
    public Phone create() {
        return new MPhone();
    }
}
