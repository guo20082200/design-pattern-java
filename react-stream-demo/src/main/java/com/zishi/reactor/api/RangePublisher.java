package com.zishi.reactor.api;

import org.reactivestreams.*;

/**
 * 一个Publisher的同步的实现，可以被多次订阅
 * 数据单调递增
 */
public final class RangePublisher implements Publisher<Integer> {

    /**
     * 开始
     */
    final int start;

    /**
     * 发射的个数
     */
    final int count;

    public RangePublisher(int start, int count) {
        this.start = start;
        this.count = count;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {

        if (subscriber == null) {
            throw new RuntimeException("Subscriber is null");
        }

        try {
            subscriber.onSubscribe(new RangeSubscription(subscriber, start, start + count));
        } catch (Throwable ex) {
            new IllegalStateException(subscriber + " violated the Reactive Streams rule 2.13 " +
                    "by throwing an exception from onSubscribe.", ex)
                    // When onSubscribe fails this way, we don't know what state the
                    // subscriber is thus calling onError may cause more crashes.
                    .printStackTrace();
        }
    }

}