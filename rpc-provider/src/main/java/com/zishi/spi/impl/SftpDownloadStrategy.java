package com.zishi.spi.impl;

import com.zishi.spi.DownloadStrategy;

public class SftpDownloadStrategy implements DownloadStrategy {
    @Override
    public void download() {
        System.out.println("采用SFTP方式下载");
    }
}
