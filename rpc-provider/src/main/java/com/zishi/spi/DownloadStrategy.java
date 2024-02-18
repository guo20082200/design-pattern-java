package com.zishi.spi;

import org.apache.dubbo.common.extension.SPI;

@SPI("abc")
public interface DownloadStrategy {

    void download();
}