package com.zishi.jdk.react.a01;

import java.util.concurrent.Flow;

public class SimpleSubscriber implements Flow.Subscriber<Integer> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        // 请求订阅者处理的元素数量
        subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("Received item: " + item);
        // 处理完一个元素后请求下一个
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error occurred: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Processing completed.");
    }
}
