package com.zishi.creational.factory.im02;

import com.zishi.creational.factory.im01.MPhone;
import com.zishi.creational.factory.im01.Phone;

public class MPhoneFactory implements PhoneFactory{
    @Override
    public Phone create() {
        return new MPhone();
    }
}
