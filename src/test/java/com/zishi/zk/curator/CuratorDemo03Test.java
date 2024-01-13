package com.zishi.zk.curator;


import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 1. 获取节点列表
 * 获取节点内容
 * //获取某个节点数据
 * client.getData().forPath(nodePath)
 * //读取zookeeper的数据，并放到Stat中
 * client.getData().storingStatIn(stat1).forPath(nodePath)
 * 获取子节点列表
 * //获取某个节点的所有子节点
 * client.getChildren().forPath(nodePath)
 */
public class CuratorDemo03Test {

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

        // start()开始连接，没有此会报错
        client.start();
    }

    @AfterEach
    void teardown() {
        client.close();
    }

    @Test
    void getNodeTest() throws Exception {
    }

    /**
     * <p>
     * 创建节点，并支持赋值数据内容
     */

    public void getNode(String nodePath) throws Exception {
        //获取某个节点数据
        byte[] bytes = client.getData().forPath(nodePath);
        System.out.println(StringUtils.join("节点：【", nodePath, "】，数据：", new String(bytes)));

        //读取zookeeper的数据，并放到Stat中
        Stat stat1 = new Stat();
        byte[] bytes2 = client.getData().storingStatIn(stat1).forPath(nodePath);
        System.out.println(StringUtils.join("节点：【", nodePath, "】，数据：", new String(bytes2)));
        System.out.println(stat1);

        //获取某个节点的所有子节点
        List<String> stringList = client.getChildren().forPath(nodePath);
        if (stringList.isEmpty()) {
            return;
        }
        //遍历节点
        stringList.forEach(System.out::println);
    }

}
