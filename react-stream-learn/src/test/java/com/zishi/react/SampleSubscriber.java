package com.zishi.react;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;


public class SampleSubscriber<T> extends BaseSubscriber<T> {


    @Override
    public void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscribed");
        request(3);
    }

    @Override
    public void hookOnNext(T value) {
        System.out.println(".................");
        System.out.println(value);
        request(1);
    }

    @Override
    protected void hookFinally(SignalType type) {
        super.hookFinally(type);
    }

    @Override
    protected void hookOnCancel() {
        super.hookOnCancel();
    }

    @Override
    protected void hookOnError(Throwable throwable) {
        super.hookOnError(throwable);
    }

    @Override
    protected void hookOnComplete() {
        super.hookOnComplete();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean isDisposed() {
        return super.isDisposed();
    }

    @Override
    protected Subscription upstream() {
        return super.upstream();
    }

    @Override
    public Context currentContext() {
        return super.currentContext();
    }
}