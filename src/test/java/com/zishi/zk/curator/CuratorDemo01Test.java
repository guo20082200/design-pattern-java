package com.zishi.zk.curator;


import org.junit.jupiter.api.Test;

/**
 * 1. 创建Curator连接对象
 * public static CuratorFramework newClient(String connectString, RetryPolicy retryPolicy)
 * public static CuratorFramework newClient(String connectString, int sessionTimeoutMs, int connectionTimeoutMs, RetryPolicy retryPolicy)
 */
public class CuratorDemo01Test extends CuratorBaseTest {


    @Test
    void testCreate01() {

        System.out.println(client);

    }


}
