package com.zishi.jdk.react.a00;

import java.util.concurrent.Flow;

public class MyPublisher implements Flow.Publisher<String>{
    MySubscription<String> subscription;
    public int request ;
    public void publish(String item){
        subscription.items.add(item);
        while (true) {
            if (request > 0) {
                for (int i = 0; i < request; i++) {
                    if (!subscription.items.isEmpty()) {
                        try {
                            Object o = subscription.items.get(subscription.items.size() - 1);
                            subscription.subscriber.onNext(o.toString());
                            subscription.items.remove(o);
                        }catch (Exception e){
                            subscription.subscriber.onError(e);
                            return;
                        }
                    }

                }
            }
            if (subscription.items.isEmpty()) {
                break;
            }
        }
    }

    @Override
    public void subscribe(Flow.Subscriber<? super String> subscriber) {
        System.out.println("第一步：绑定订阅者" );
        MySubscription<String> subscription = new MySubscription<>(subscriber,this);
        this.subscription = subscription;
        subscriber.onSubscribe(subscription);
    }

}