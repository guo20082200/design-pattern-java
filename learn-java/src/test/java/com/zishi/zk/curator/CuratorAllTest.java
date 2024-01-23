package com.zishi.zk.curator;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.RetryOneTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 1. 创建Curator连接对象
 * public static CuratorFramework newClient(String connectString, RetryPolicy retryPolicy)
 * public static CuratorFramework newClient(String connectString, int sessionTimeoutMs, int connectionTimeoutMs, RetryPolicy retryPolicy)
 */
public class CuratorAllTest {

    private static final String ZK_ADDRESS = "172.16.23.77:2181";
    private static final int connectionTimeout = 30000; // ms
    private static final int sessionTimeout = 50000; // ms

    @Test
    void createTest() {
        RetryPolicy policy = new RetryOneTime(3000);
        //老版本的方式，创建zookeeper连接客户端
        CuratorFramework client = CuratorFrameworkFactory.builder().
                connectString(ZK_ADDRESS).
                sessionTimeoutMs(sessionTimeout).
                connectionTimeoutMs(connectionTimeout).
                retryPolicy(policy).
                build();

        Assertions.assertNotNull(client);
        Assertions.assertEquals(CuratorFrameworkState.LATENT, client.getState());
        client.start();
        Assertions.assertEquals(CuratorFrameworkState.STARTED, client.getState());

        client.close();

        System.out.println("aaaaaaaaaaaaaaa");
    }
}
