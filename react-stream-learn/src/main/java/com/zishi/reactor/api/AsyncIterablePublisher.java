package com.zishi.reactor.api;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.concurrent.Executor;

/**
 * AsyncIterablePublisher is an implementation of Reactive Streams `Publisher`
 * which executes asynchronously, using a provided `Executor` and produces elements
 * from a given `Iterable` in a "unicast" configuration to its `Subscribers`.
 * <p>
 * NOTE: The code below uses a lot of try-catches to show the reader where exceptions can be expected, and where they are forbidden.
 */
public class AsyncIterablePublisher<T> implements Publisher<T> {
    private final static int DEFAULT_BATCHSIZE = 1024;

    private final Iterable<T> elements; // This is our data source / generator
    private final Executor executor; // This is our thread pool, which will make sure that our Publisher runs asynchronously to its Subscribers
    private final int batchSize; // In general, if one uses an `Executor`, one should be nice nad not hog a thread for too long, this is the cap for that, in elements

    public AsyncIterablePublisher(final Iterable<T> elements, final Executor executor) {
        this(elements, DEFAULT_BATCHSIZE, executor);
    }

    public AsyncIterablePublisher(final Iterable<T> elements, final int batchSize, final Executor executor) {
        if (elements == null) {
            throw null;
        }
        if (executor == null) {
            throw null;
        }
        if (batchSize < 1) {
            throw new IllegalArgumentException("batchSize must be greater than zero!");
        }
        this.elements = elements;
        this.executor = executor;
        this.batchSize = batchSize;
    }

    @Override
    public void subscribe(final Subscriber<? super T> s) {
        // As per rule 1.11, we have decided to support multiple subscribers in a unicast configuration
        // for this `Publisher` implementation.
        // As per 2.13, this method must return normally (i.e. not throw)
        new SubscriptionImpl(s).init();
    }
}