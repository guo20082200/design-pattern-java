package com.zishi.behavioral.chain;
public class Test {

    @org.junit.Test
    public void testFilter(){

        String msg = "<html>敏感字眼</html>";

        Request request = new Request();
        request.setRequestStr(msg);
        Response response = new Response();
        response.setResponseStr("response------------");

        FilterChain fc = new FilterChain();
        fc.addFilter(new HTMLFilter()).addFilter(new SensitiveFilter());

        fc.doFilter(request, response, fc);
        System.out.println(request.getRequestStr());
        System.out.println(response.getResponseStr());

    }

}