package com.zishi.reactor.api;

public final class Request implements Signal {
    final long n;

    Request(final long n) {
        this.n = n;
    }
}