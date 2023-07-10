package com.zishi.behavioral.chain;

public class SensitiveFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response,
                         FilterChain filterChain) {
        request.setRequestStr(request.getRequestStr().replace("敏感", "幸福") + "---SensitiveFilter()");
        filterChain.doFilter(request, response, filterChain);
        response.setResponseStr(response.getResponseStr() + "---SensitiveFilter()");
    }

}