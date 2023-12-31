package com.zishi.pattern.behavioral.chain;
public class HTMLFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response,
            FilterChain filterChain) {
        request.setRequestStr(request.getRequestStr().replace('<', '[').replace(">", "]")+"---HTMLFilter()");
        filterChain.doFilter(request, response, filterChain);
        response.setResponseStr(response.getResponseStr()+"---HTMLFilter()");
    }

}