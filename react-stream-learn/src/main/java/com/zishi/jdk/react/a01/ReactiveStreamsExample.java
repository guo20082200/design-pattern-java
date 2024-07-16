package com.zishi.jdk.react.a01;


public class ReactiveStreamsExample {

    public static void main(String[] args) throws InterruptedException {
        // 创建发布者和订阅者
        SimplePublisher simplePublisher = new SimplePublisher();
        SimpleSubscriber simpleSubscriber = new SimpleSubscriber();

        // 订阅者订阅发布者
        simplePublisher.subscribe(simpleSubscriber);

        // 发布者发布数据
        simplePublisher.publishItems();
        // 睡一觉，确保数据处理完成
        Thread.sleep(3000);
    }
}
