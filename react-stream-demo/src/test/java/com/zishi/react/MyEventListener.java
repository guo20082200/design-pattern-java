package com.zishi.react;

import java.util.List;

public interface MyEventListener<T> {
    void onDataChunk(List<T> chunk); // 产生数据

    void processComplete(); // 处理完成
}