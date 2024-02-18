package com.zishi.spi.impl;

import com.zishi.spi.DownloadStrategy;

public class HttpDownloadStrategy implements DownloadStrategy {
    @Override
    public void download() {
        System.out.println("采用Http下载方式");
    }
}
