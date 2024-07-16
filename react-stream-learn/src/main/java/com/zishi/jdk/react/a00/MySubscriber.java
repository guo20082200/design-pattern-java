package com.zishi.jdk.react.a00;

import java.util.concurrent.Flow;

public class MySubscriber implements Flow.Subscriber<String> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("第二步：接收Subscription");
        this.subscription = subscription;
        // 请求订阅者处理的元素数量
        subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        System.out.println("第四步：推送数据");
        System.out.println("MySubscriber 消费了item = " + item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("出异常了 = " + throwable);
    }

    @Override
    public void onComplete() {

    }

}