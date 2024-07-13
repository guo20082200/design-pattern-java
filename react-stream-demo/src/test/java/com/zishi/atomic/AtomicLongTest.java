package com.zishi.atomic;


import com.google.common.util.concurrent.FutureCallback;
import org.junit.jupiter.api.Test;

import javax.security.auth.callback.Callback;
import java.util.concurrent.atomic.AtomicLong;

class AtomicLongTest {


    @Test
    void testAddAndGet() {

        AtomicLong atomicLong = new AtomicLong();
        long addAndGet = atomicLong.addAndGet(1);
        System.out.println(addAndGet);
    }

    @Test
    void testCallBack() {

        FutureCallback<String> callback = new FutureCallback<>() {

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        };

    }
}















