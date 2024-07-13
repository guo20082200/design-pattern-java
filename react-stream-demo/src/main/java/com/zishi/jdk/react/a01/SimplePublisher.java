package com.zishi.jdk.react.a01;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class SimplePublisher implements Flow.Publisher<Integer> {

    private final SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

    public void publishItems() {
        for (int i = 1; i <= 5; i++) {
            publisher.submit(i);
        }

        // 发布者完成发布
        publisher.close();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        publisher.subscribe(subscriber);
    }
}