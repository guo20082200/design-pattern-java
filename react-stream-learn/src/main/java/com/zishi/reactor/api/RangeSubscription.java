package com.zishi.reactor.api;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 1. 订阅关系，持有下游的对象， request() 和 cancel()
 * 2. 继承 AtomicLong， 不并发的执行，
 * 3. The atomic transition from 0L to N > 0L will ensure this.
 */
public final class RangeSubscription extends AtomicLong implements Subscription {

    private static final long serialVersionUID = -9000845542177067735L;

    /**
     * 订阅者
     */
    final Subscriber<? super Integer> downstream;

    /**
     * 结束的值（不包括）
     */
    final int end;

    /**
     * 在范围[start, start + count)里面的当前的索引，
     * 在调用 downstream.onNext()的时候将会被emitted
     */
    int index;

    /**
     * Indicates the emission should stop.
     */
    volatile boolean cancelled;

    /**
     * Holds onto the IllegalArgumentException (containing the offending stacktrace)
     * indicating there was a non-positive request() call from the downstream.
     */
    volatile Throwable invalidRequest;


    /**
     * 构造一个有状态的RangeSubscription，可以发射（from an integer range of [start, end).）信号给指定的下游订阅者
     * @param downstream 下游订阅者
     * @param start 开始数据
     * @param end 结束数据-不包含
     */
    public RangeSubscription(Subscriber<? super Integer> downstream, int start, int end) {
        this.downstream = downstream;
        this.index = start;
        this.end = end;
    }

    /**
     * 1. 没有事件通过一个Publisher发送，直到通过该方法发送一个demand信号
     * 2.
     * @param n the strictly positive number of elements to requests to the upstream
     */
    @Override
    public void request(long n) {
        // Non-positive requests should be honored with IllegalArgumentException
        if (n <= 0L) {
            invalidRequest = new IllegalArgumentException("§3.9: non-positive requests are not allowed!");
            n = 1;
        }
        // Downstream requests are cumulative and may come from any thread
        for (; ; ) {
            long requested = get();
            long update = requested + n;
            // As governed by rule 3.17, when demand overflows `Long.MAX_VALUE`
            // we treat the signalled demand as "effectively unbounded"
            if (update < 0L) {
                update = Long.MAX_VALUE;
            }
            // atomically update the current requested amount
            if (compareAndSet(requested, update)) {
                // if there was no prior request amount, we start the emission loop
                if (requested == 0L) {
                    emit(update);
                }
                break;
            }
        }
    }

    /**
     * 处理取消请求，
     * 幂等，线程安全，
     * 不同步执行密集型计算
     */
    @Override
    public void cancel() {
        // Indicate to the emission loop it should stop.
        cancelled = true;
    }

    void emit(long currentRequested) {
        // Load fields to avoid re-reading them from memory due to volatile accesses in the loop.
        Subscriber<? super Integer> downstream = this.downstream;
        int index = this.index;
        int end = this.end;
        int emitted = 0;

        try {
            for (; ; ) {
                // 检查是否为有效的请求，如果有就记录异常
                // 异常的堆栈信息可以帮助定位Subscriber中错误逻辑的位置
                Throwable invalidRequest = this.invalidRequest;
                if (invalidRequest != null) {
                    // 当存在异常，信号是 onError，subscription 订阅要被取消
                    cancelled = true;
                    downstream.onError(invalidRequest);
                    return;
                }

                // 循环发送数据
                while (index != end && emitted != currentRequested) {
                    if (cancelled) {
                        return;
                    }
                    downstream.onNext(index);
                    // index 加1
                    index++;
                    //emitted增加1，防止 overflowing the downstream.
                    emitted++;
                }

                // 如果达到结尾，结束downstream
                if (index == end) {
                    // 如果流没有被取消
                    if (!cancelled) {
                        // 取消流
                        cancelled = true;
                        // 结束流
                        downstream.onComplete();
                    }
                    return;
                }

                // 当我们循环的时候，请求的数量（requested amount）改变了么？
                long freshRequested = get();
                if (freshRequested == currentRequested) {
                    // 没有改变
                    // Save where the loop has left off: the next value to be emitted
                    this.index = index;
                    // Atomically subtract the previously requested (also emitted) amount
                    currentRequested = addAndGet(-currentRequested);
                    // If there was no new request in between get() and addAndGet(), we simply quit
                    // The next 0 to N transition in request() will trigger the next emission loop.
                    if (currentRequested == 0L) {
                        break;
                    }
                    // 看起来像是有很多异步的请求，然后重置emitted的计数并继续
                    emitted = 0;
                } else {
                    // 改变了。为了避免原子减少和消费， index仍旧指向下一个发送的值
                    currentRequested = freshRequested;
                }
            }
        } catch (Throwable ex) {
            // We can only get here if `onNext`, `onError` or `onComplete` threw, and they
            // are not allowed to according to 2.13, so we can only cancel and log here.
            // If `onError` throws an exception, this is a spec violation according to rule 1.9,
            // and all we can do is to log it.

            // Make sure that we are cancelled, since we cannot do anything else
            // since the `Subscriber` is faulty.
            cancelled = true;
            (new IllegalStateException(downstream + " violated the Reactive Streams rule 2.13 by " +
                    "throwing an exception from onNext, onError or onComplete.", ex))
                    .printStackTrace();
        }
    }
}