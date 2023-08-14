package com.zishi.pattern.creational.factory.im03;

import com.zishi.pattern.creational.factory.im01.Phone;

public interface Factory {

    public Phone createPhone();

    public Book createBook();
}
