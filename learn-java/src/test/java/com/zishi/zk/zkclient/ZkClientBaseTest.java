package com.zishi.zk.zkclient;


import org.I0Itec.zkclient.ZkClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * ZkClient客户端也不错，就是更新的慢，感觉不怎么维护
 */
@DisplayName("ZkClient所有api测试样例")
public class ZkClientBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkClientBaseTest.class);
    private static final String ADDR = "172.16.23.77:2181";
    private static final int connectionTimeout = 5000; // ms
    private static final int sessionTimeout = 5000; // ms

    ZkClient zkClient = null;

    @BeforeEach
    void setUp() {
        LOGGER.info("创建ZkClient....");
        System.setProperty("zookeeper.sasl.client", "false");
        zkClient = new ZkClient(ADDR, sessionTimeout, connectionTimeout);
        zkClient.waitUntilConnected(60, TimeUnit.SECONDS);
    }

    @AfterEach
    void tearDown() {
        LOGGER.info("关闭ZkClient....");
        zkClient.close();
    }

}
