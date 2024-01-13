package com.zishi.zk;


import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.*;

/**
 * 删除Znode节点
 * public boolean delete(final String path)
 * //根据节点路径，版本号，删除节点
 * public boolean delete(final String path, final int version)
 * //递归删除当前节点，以及当前节点的子节点
 * public boolean deleteRecursive(String path)
 */
class ZkClientDemo05Test {

    private static final String ADDR = "localhost:2181";
    private static final int connectionTimeout = 5000;
    private static final int sessionTimeout = 5000;

    ZkClient zkClient = null;
    ZkSerializer zkSerializer = null;

    @BeforeEach
    void setUp() {
        zkClient = new ZkClient(ADDR, sessionTimeout, connectionTimeout);
    }

    @AfterEach
    void tearDown() {
        zkClient.close();
    }

    @Test
    void testDelete01() {

    }
}
