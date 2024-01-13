package com.zishi.zk;


import org.I0Itec.zkclient.IZkConnection;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.*;

/**
 * 创建Znode节点：
 * 1. 创建节点
 * public String create(final String path, Object data, final CreateMode mode) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException
 * 2. 创建短暂节点
 * public void createEphemeral(final String path) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException
 * 3. 创建短暂节点
 * public void createEphemeral(final String path, final Object data) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException
 * 4. 创建短暂序列节点
 * public String createEphemeralSequential(final String path, final Object data) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException
 * 5. 创建持久节点
 * public void createPersistent(String path) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException
 * 6. 创建持久节点
 * public void createPersistent(String path, boolean createParents) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException
 * 7. 创建持久节点
 * public void createPersistent(String path, Object data) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException
 * 8. 创建序列节点
 * public String createPersistentSequential(String path, Object data) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException
 */
class ZkClientDemo02Test extends ZkClientBaseTest {

    /**
     * void createEphemeral(final String path)
     * createEphemeral(final String path, final Object data)
     * String createEphemeralSequential(final String path, final Object data)
     *
     */
    @Test
    void testCreate01() {
        zkClient.createEphemeral("/testCreateEphemeral");
        zkClient.createEphemeral("/testCreateEphemeral2", "eafer");
        zkClient.createEphemeralSequential("/testCreateEphemeralSequential", "createEphemeralSequential");
    }

    /**
     * createPersistent(String path)
     * createPersistent(String path, boolean createParents)
     * createPersistent(String path, Object data)
     * createPersistentSequential(String path, Object data)
     */
    @Test
    void testCreate02() {
        zkClient.createPersistent("/testCreatePersistent");
        zkClient.createPersistent("/aerfaerf/erfaery56", true);
        zkClient.createPersistent("/testCreatePersistent2", "Persistent");
        zkClient.createPersistentSequential("/testCreatePersistentSequential", "PersistentSequential");
    }

    /**
     * create(final String path, Object data, final CreateMode mode)
     */
    @Test
    void testCreate03() {
        zkClient.create("/testCreateEphemeral", null, CreateMode.EPHEMERAL);
        zkClient.create("/testCreatePersistentSequential", "PersistentSequential",  CreateMode.PERSISTENT_SEQUENTIAL);
    }
}
