package com.zishi.zk.zkclient;


import org.I0Itec.zkclient.IZkConnection;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.*;

/**
 * ZkClient客户端也不错，就是更新的慢，感觉不怎么维护
 */
@DisplayName("ZkClient所有api测试样例")
public class ZkClientAllTest {
    private static final String ADDR = "localhost:2181";
    private static final int connectionTimeout = 5000; // ms
    private static final int sessionTimeout = 5000; // ms

    @Nested
    @DisplayName("ZkClientCreate")
    public class ZkClientCreateTest {
        ZkClient zkClient = null;
        ZkSerializer zkSerializer = null;
        @BeforeEach
        void init() {
            zkSerializer = new SerializableSerializer();
        }

        @AfterEach
        void teardown() {
            zkClient.close();
        }
        @Test
        void testCreate01() {
            zkClient = new ZkClient(ADDR);
            System.out.println(zkClient);
        }

        @Test
        void testCreate02() {
            zkClient = new ZkClient(ADDR, sessionTimeout, connectionTimeout, zkSerializer);
            System.out.println(zkClient);
        }

        @Test
        void testCreate03() {
            IZkConnection connection =new ZkConnection(ADDR);
            zkClient = new ZkClient(connection);
            System.out.println(zkClient);
        }
    }

    @Nested
    @DisplayName("ZNodeCreateTest")
    public class ZNodeCreateTest extends ZkClientBaseTest {

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


    @Nested
    @DisplayName("ZNodeReadTest")
    public class ZNodeReadTest extends ZkClientBaseTest {

    }

    @Nested
    @DisplayName("ZNodeUpdateTest")
    public class ZNodeUpdateTest extends ZkClientBaseTest {

    }

    @Nested
    @DisplayName("ZNodeDeleteTest")
    public class ZNodeDeleteTest extends ZkClientBaseTest {

    }




}
