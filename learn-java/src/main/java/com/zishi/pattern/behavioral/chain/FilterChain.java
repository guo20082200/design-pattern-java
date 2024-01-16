package com.zishi.pattern.behavioral.chain;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter {

    private List<Filter> filters = new ArrayList<Filter>();
    int index = 0;    //标记执行到第几个filter

    //把函数的返回值设为FilterChain，返回this,就能方便链式编写代码
    public FilterChain addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    public void doFilter(Request request, Response response, FilterChain fc) {
        if (index == filters.size()) {
            return;
        }
        Filter f = filters.get(index);
        index++;
        f.doFilter(request, response, fc);
    }

}