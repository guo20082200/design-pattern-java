package com.zishi.react;

import org.testng.collections.Lists;

import java.util.List;

public class MyEventProcessor<T> {

    public void register(MyEventListener<T> eventListener) {
        List data = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            data.add("xx " + i);
        }
        eventListener.onDataChunk(data);
        eventListener.processComplete();
    }
}
