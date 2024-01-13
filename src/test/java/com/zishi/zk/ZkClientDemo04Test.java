package com.zishi.zk;


import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 设置和修改节点内容
 * // 为节点设置内容
 * public void writeData(String path, Object data)
 * // 为节点的某个版本，设置内容
 * public void writeData(final String path, Object data, final int expectedVersion)
 * //该方式设置内容更加的灵活，可以使用DataUpdater<T>设置内容
 * public <T> void updateDataSerialized(String path, DataUpdater<T> updater)
 */
class ZkClientDemo04Test {

    private static final String ADDR = "localhost:2181";
    private static final int connectionTimeout = 5000;
    private static final int sessionTimeout = 5000;

    static ZkClient zkClient = null;
    ZkSerializer zkSerializer = null;

    @BeforeAll
    static void setUp() {
        zkClient = new ZkClient(ADDR, sessionTimeout, connectionTimeout);
    }

    @AfterAll
    static void tearDown() {
        zkClient.close();
    }

    @Test
    void testCreate01() {
    }

}
