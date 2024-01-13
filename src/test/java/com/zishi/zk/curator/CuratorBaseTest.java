package com.zishi.zk.curator;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class CuratorBaseTest {

    private static final String ZK_ADDRESS = "localhost:2181";
    private static final int connectionTimeout = 10000; // ms
    private static final int sessionTimeout = 5000; // ms


    CuratorFramework client = null;

    @BeforeEach
    void init() {
        RetryPolicy policy = new RetryOneTime(3000);
        //老版本的方式，创建zookeeper连接客户端
        client = CuratorFrameworkFactory.builder().
                connectString(ZK_ADDRESS).
                sessionTimeoutMs(sessionTimeout).
                connectionTimeoutMs(connectionTimeout).
                retryPolicy(policy).
                build();
    }

    @AfterEach
    void teardown() {
        client.close();
    }

}
