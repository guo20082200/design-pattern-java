package com.zishi.pattern.creational.factory.im03;

import com.zishi.pattern.creational.factory.im01.MPhone;
import com.zishi.pattern.creational.factory.im01.Phone;

public class XiaoMiFactory implements Factory{
    @Override
    public Phone createPhone() {
        return new MPhone();
    }

    @Override
    public Book createBook() {
        return new MiBook();
    }
}
