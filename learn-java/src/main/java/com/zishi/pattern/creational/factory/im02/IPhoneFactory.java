package com.zishi.pattern.creational.factory.im02;

import com.zishi.pattern.creational.factory.im01.IPhone;
import com.zishi.pattern.creational.factory.im01.Phone;

public class IPhoneFactory implements PhoneFactory{
    @Override
    public Phone create() {
        return new IPhone();
    }
}
