package com.zishi.creational.factory.im03;

import com.zishi.creational.factory.im01.IPhone;
import com.zishi.creational.factory.im01.Phone;

public class AppleFactory implements Factory {
    @Override
    public Phone createPhone() {
        return new IPhone();
    }

    @Override
    public Book createBook() {
        return new MacBook();
    }
}
