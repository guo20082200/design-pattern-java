package com.zishi.pattern.behavioral.template;

public class TestDemo {

    public static void main(String[] args) {
        //此处省略若干代码
        LivePlay tencentLive = new TencentLivePlay();
        tencentLive.seeLivePlay();

        System.out.println("");

        LivePlay jinShanLive = new JinShanLivePlay();
        jinShanLive.seeLivePlay();
    }
}
