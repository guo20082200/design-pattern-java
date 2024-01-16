package com.zishi.zk.curator;


import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 1. 设置和修改节点内容
 * 	//更新节点
 * 	client.setData().forPath(nodePath, data.getBytes())
 * 	//指定版本号，更新节点
 * 	client.setData().withVersion(-1).forPath(nodePath, data.getBytes())
 * 	//异步设置某个节点数据
 * 	client.setData().inBackground().forPath(nodePath, data.getBytes())
 *
 */
public class CuratorDemo04Test {

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
    void updateNodeTest() throws Exception {

    }

    /**
     * <p>
     * 设置（修改）节点数据
     * <p/>
     *
     * @param nodePath
     * @param data
     * @return void
     * @Date 2020/6/21 13:46
     */
    public void updateNode(String nodePath, String data) throws Exception {
        //更新节点
        Stat stat = client.setData().forPath(nodePath, data.getBytes());
        //指定版本号，更新节点，更新的时候如果指定数据版本的话，那么需要和zookeeper中当前数据的版本要一致，-1表示匹配任何版本
        //Stat stat = client.setData().withVersion(-1).forPath(nodePath, data.getBytes());
        System.out.println(stat);

        //异步设置某个节点数据
        Stat stat1 = client.setData().inBackground().forPath(nodePath, data.getBytes());
        System.out.println(stat1.toString());
    }




}
