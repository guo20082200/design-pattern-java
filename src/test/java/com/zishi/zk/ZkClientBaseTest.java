package com.zishi.zk;


import org.I0Itec.zkclient.IZkConnection;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.*;

/**
 * ZkClient客户端也不错，就是更新的慢，感觉不怎么维护
 */
@DisplayName("ZkClient所有api测试样例")
public class ZkClientBaseTest {
    private static final String ADDR = "localhost:2181";
    private static final int connectionTimeout = 5000; // ms
    private static final int sessionTimeout = 5000; // ms

    ZkClient zkClient = null;

    @BeforeEach
    void setUp() {
        zkClient = new ZkClient(ADDR, sessionTimeout, connectionTimeout);
    }

    @AfterEach
    void tearDown() {
        zkClient.close();
    }

}
