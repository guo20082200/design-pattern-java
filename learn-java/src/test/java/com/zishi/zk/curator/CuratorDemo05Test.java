package com.zishi.zk.curator;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 1. 删除zNode节点
 * //删除节点
 * client.delete().forPath(nodePath);
 * <p>
 * //删除节点，即使出现网络故障，zookeeper也可以保证删除该节点
 * client.delete().guaranteed().forPath(nodePath);
 * <p>
 * //级联删除节点（如果当前节点有子节点，子节点也可以一同删除）
 * client.delete().deletingChildrenIfNeeded().forPath(nodePath);
 */
public class CuratorDemo05Test {

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
    void deleteNodeTest() throws Exception {

    }

    public void deleteNode(String nodePath) throws Exception {
        //删除节点
        //client.delete().forPath(nodePath);

        //删除节点，即使出现网络故障，zookeeper也可以保证删除该节点
        //client.delete().guaranteed().forPath(nodePath);

        //级联删除节点（如果当前节点有子节点，子节点也可以一同删除）
        client.delete().deletingChildrenIfNeeded().forPath(nodePath);
    }


}
