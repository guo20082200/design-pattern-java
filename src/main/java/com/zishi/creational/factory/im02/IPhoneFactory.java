package com.zishi.creational.factory.im02;

import com.zishi.creational.factory.im01.IPhone;
import com.zishi.creational.factory.im01.Phone;

public class IPhoneFactory implements PhoneFactory{
    @Override
    public Phone create() {
        return new IPhone();
    }
}
