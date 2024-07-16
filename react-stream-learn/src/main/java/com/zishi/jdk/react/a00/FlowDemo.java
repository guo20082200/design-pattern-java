package com.zishi.jdk.react.a00;


public class FlowDemo {

    public static void main(String[] args) {
        MyPublisher myPublisher = new MyPublisher();
        MySubscriber mySubscriber = new MySubscriber();
        myPublisher.subscribe(mySubscriber);
        myPublisher.publish("111");
        myPublisher.publish("222");
        myPublisher.publish(null);
    }
}
