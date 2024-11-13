package com.zishi.juc.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zishi
 * @param <E>
 */
public class BoundedBuffer<E> {

    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final List<E> items = Collections.synchronizedList(new ArrayList<>());
    int putPtr, takePtr, count;

    public void put(E x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.size()) {
                notFull.await();
            }
            items.set(putPtr, x);
            if (++putPtr == items.size()) {
                putPtr = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            E x = items.get(takePtr);
            if (++takePtr == items.size()) {
                takePtr = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}