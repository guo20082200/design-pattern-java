package com.zishi.zk.zkclient;


import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 创建Znode节点：
 * <p>
 * 1. 获取api
 * // 判断节点是否存在，通常情况下，不存在节点，采取创建节点，否则会报错
 * public boolean exists(final String path)
 * public <T extends Object> T readData(String path)
 * public <T extends Object> T readData(String path, boolean returnNullIfPathNotExists)
 * public <T extends Object> T readData(String path, Stat stat)
 * // 不在同一个包下无法调用
 * protected <T extends Object> T readData(final String path, final Stat stat, final boolean watch)
 * public void subscribeDataChanges(String path, IZkDataListener listener)
 * <p>
 * 2. 获取子节点列表
 * // 获取当前节点的子节点
 * public List<String> getChildren(String path)
 * // 获取当前节点的子节点的数量
 * public int countChildren(String path)
 * // 不在同一个包下无法调用，watch：是否使用默认的Watcher
 * protected List<String> getChildren(final String path, final boolean watch)
 * // 订阅当前节点的子节点的变化
 * public List<String> subscribeChildChanges(String path, IZkChildListener listener)
 */
class ZkClientDemo03Test {

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
    void testCreate01() {

        //1. 判断节点是否存在，通常情况下，不存在节点，采取创建节点，否则会报错
        boolean existsed = zkClient.exists("/testCreatePersistent");
        Assertions.assertTrue(existsed);
        // 2.
        Object data = zkClient.readData("/testCreatePersistent2");
        Assertions.assertEquals(data, "Persistent", "xxxxxxxxx");

        // public <T extends Object> T readData(String path, boolean returnNullIfPathNotExists)
        Object data2 = zkClient.readData("/testCreatePersistent2", Boolean.TRUE);
        Assertions.assertNotNull(data2);

        // public <T extends Object> T readData(String path, Stat stat)
        Stat stat = new Stat();
        System.out.println(stat);
        Object data3 = zkClient.readData("/testCreatePersistent2", stat);
        System.out.println(data3);
        System.out.println(stat);
        // protected <T extends Object> T readData(final String path, final Stat stat, final boolean watch)

        // public void subscribeDataChanges(String path, IZkDataListener listener)

    }


}
