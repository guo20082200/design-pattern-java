package com.zishi.structure.adapter;

public class AdapterClient {
    public void recordLog() {
        LogFactory logFactory = new LogAdapter(new NbLoggerImp());
        logFactory.debug("Test", "我将使用牛逼logger打印log");
    }
}