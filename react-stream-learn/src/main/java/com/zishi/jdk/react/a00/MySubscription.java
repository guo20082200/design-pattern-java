package com.zishi.jdk.react.a00;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

public class MySubscription<T> implements Flow.Subscription {

    final Flow.Subscriber<? super T> subscriber;
    final MyPublisher publisher;
    List items = new ArrayList();

    public MySubscription(Flow.Subscriber<? super T> subscriber, MyPublisher publisher) {
        this.subscriber = subscriber;
        this.publisher = publisher;
    }

    @Override
    public void request(long n) {
        this.publisher.request++;
        System.out.println("第三步：拉取请求");
    }

    @Override
    public void cancel() {

    }
}