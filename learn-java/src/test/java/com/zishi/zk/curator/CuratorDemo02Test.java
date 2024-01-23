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
 * 1. 创建zNode节点
 * 	 public T forPath(String path)
 * 	 //创建节点，并赋值内容
 * 	 public T forPath(String path, byte[] data)
 * 	 //判断节点是否存在，节点存在了，创建时仍然会报错
 * 	 public ExistsBuilder checkExists()
 */
public class CuratorDemo02Test {

    private static final String ZK_ADDRESS = "localhost:2181";
    private static final int connectionTimeout = 10000; // ms
    private static final int sessionTimeout = 50000; // ms

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
    void createNodeTest() throws Exception {
        createNode("/eaferfaer", "123");
    }

    /**
     * <p>
     * 创建节点，并支持赋值数据内容
     */

    public void createNode(String nodePath, String data) throws Exception {
        if (StringUtils.isEmpty(nodePath)) {
            System.out.println("节点【" + nodePath + "】不能为空");
            return;
        }

        //1、对节点是否存在进行判断，否则会报错：【NodeExistsException: KeeperErrorCode = NodeExists for /root】
        Stat exists = client.checkExists().forPath(nodePath);
        if (null != exists) {
            System.out.println("节点【" + nodePath + "】已存在，不能新增");
            return;
        } else {
            System.out.println(StringUtils.join("节点【", nodePath, "】不存在，可以新增节点！"));
        }

        //2、创建节点, curator客户端开发提供了Fluent风格的API，是一种流式编码方式，可以不断地点.点.调用api方法
        //创建永久节点（默认就是持久化的）
        client.create().forPath(nodePath + System.currentTimeMillis());

        //3、手动指定节点的类型
        client.create()
                .withMode(CreateMode.PERSISTENT)
                .forPath(nodePath + System.currentTimeMillis());

        //4、如果父节点不存在，创建当前节点的父节点
        String node = client.create()
                .creatingParentsIfNeeded()
                .forPath(nodePath + System.currentTimeMillis());
        System.out.println(node);

        //创建节点，并为当前节点赋值内容
        if (StringUtils.isNotBlank(data)) {
            //5、创建永久节点，并为当前节点赋值内容
            client.create().forPath(nodePath + System.currentTimeMillis(), data.getBytes());

            //6、创建永久有序节点
            client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(nodePath + System.currentTimeMillis(), data.getBytes());

            //7、创建临时节点
            client.create()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(nodePath + System.currentTimeMillis(), data.getBytes());
        }

        //8、创建临时有序节点
        client.create()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(nodePath + System.currentTimeMillis(), data.getBytes());
    }



}
