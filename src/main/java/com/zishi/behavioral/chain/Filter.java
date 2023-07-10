package com.zishi.behavioral.chain;

public interface Filter {
    public void doFilter(Request request, Response response, FilterChain filterChain);
}