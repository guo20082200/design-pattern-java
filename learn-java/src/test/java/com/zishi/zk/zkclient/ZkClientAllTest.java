package com.zishi.zk.zkclient;


import org.I0Itec.zkclient.IZkConnection;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ZkClient客户端也不错，就是更新的慢，感觉不怎么维护
 */
@DisplayName("ZkClient所有api测试样例")
public class ZkClientAllTest {

    @Nested
    @DisplayName("ZkClientCreate")
    @Order(1)
    public class ZkClientCreateTest {
        private static final String ADDR = "172.16.23.77:2181";
        private static final int connectionTimeout = 5000; // ms
        private static final int sessionTimeout = 5000; // ms
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
            Assertions.assertNotNull(zkClient);
        }

        @Test
        void testCreate02() {
            zkClient = new ZkClient(ADDR, sessionTimeout, connectionTimeout, zkSerializer);
            Assertions.assertNotNull(zkClient);
        }

        @Test
        void testCreate03() {
            IZkConnection connection = new ZkConnection(ADDR);
            zkClient = new ZkClient(connection);
            Assertions.assertNotNull(zkClient);
        }
    }

    @Order(2)
    @Nested
    @DisplayName("ZNodeCreateTest")
    public class ZNodeCreateTest extends ZkClientBaseTest {

        /**
         * void createEphemeral(final String path)
         * createEphemeral(final String path, final Object data)
         * String createEphemeralSequential(final String path, final Object data)
         */
        @Test
        @DisplayName("createEphemeral")
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
        @DisplayName("createPersistent")
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
        @DisplayName("create")
        void testCreate03() {
            zkClient.create("/testCreateEphemeral", null, CreateMode.EPHEMERAL);
            zkClient.create("/testCreatePersistentSequential", "PersistentSequential", CreateMode.PERSISTENT_SEQUENTIAL);
        }

        @Test
        @DisplayName("createPersistentWithParent")
        void createPersistentWithParent() {
            // public void createPersistent(String path, boolean createParents, List<ACL> acl) throws ZkInterruptedException,
            zkClient.createPersistent("/a/b/c/d/e", true);

        }
    }


    @Order(3)
    @Nested
    @DisplayName("ZNodeReadTest")
    public class ZNodeReadTest extends ZkClientBaseTest {

        @Test
        @DisplayName("判断path是否存在")
        void testExists() {
            //1. 判断节点是否存在，通常情况下，不存在节点，采取创建节点，否则会报错
            boolean existsed = zkClient.exists("/testCreatePersistent");
            Assertions.assertTrue(existsed);
        }

        @Test
        @DisplayName("读取指定路径的数据")
        void testReadData() {
            Object data = zkClient.readData("/testCreatePersistent2");
            Assertions.assertEquals(data, "Persistent", "xxxxxxxxx");
        }

        @Test
        @DisplayName("读取指定路径的数据，如果路径不存在返回null")
        void testReadDataReturnNullIfPathNotExists() {
            // public <T extends Object> T readData(String path, boolean returnNullIfPathNotExists)
            Object data2 = zkClient.readData("/testCreatePersistent2", Boolean.TRUE);
            Assertions.assertNotNull(data2);

            Object data3 = zkClient.readData("/returnNullIfPathNotExists", Boolean.TRUE);
            Assertions.assertNull(data3);
        }

        @Test
        @DisplayName("读取指定路径的数据，返回Stat统计结果")
        void testReadDataReturnStat() {
            Stat stat = new Stat();
            Object data3 = zkClient.readData("/testCreatePersistent2", stat);
            Assertions.assertEquals(data3, "Persistent", "....");
            System.out.println(stat);
        }

    }

    @Order(4)
    @Nested
    @DisplayName("Update-设置和修改节点内容")
    public class ZNodeUpdateTest extends ZkClientBaseTest {
        private final Logger LOGGER = LoggerFactory.getLogger(ZNodeUpdateTest.class);

        /**
         * 设置和修改节点内容
         * // 为节点设置内容
         * <p>
         * <p>
         */

        @Test
        @DisplayName("testWriteData")
        void testWriteData() {
            //1. 为节点设置内容 public void writeData(String path, Object data)
            zkClient.writeData("/testCreatePersistent2", "newdata");
            Object data3 = zkClient.readData("/testCreatePersistent2");
            Assertions.assertEquals(data3, "newdata", "....");
        }

        @Test
        @DisplayName("读取指定路径的数据")
        void testReadData() {
            // 为节点的某个版本，设置内容 public void writeData(final String path, Object data, final int expectedVersion)
            Stat stat = new Stat();
            Object data = zkClient.readData("/testCreatePersistent2", stat);
            LOGGER.info("data的版本为： {}", stat.getVersion());
            zkClient.writeData("/testCreatePersistent2", "newdata3", stat.getVersion());

            Stat stat2 = new Stat();
            Object data3 = zkClient.readData("/testCreatePersistent2", stat2);
            Assertions.assertEquals(data3, "newdata3", "xxxxxxxxx");
            LOGGER.info("更新之后data的版本为： {}", stat2.getVersion());

            Assertions.assertEquals(stat2.getVersion() - stat.getVersion(), 1);

        }

        @Test
        @DisplayName("testUpdateDataSerializedDataUpdater")
        void testUpdateDataSerializedDataUpdater() {
            //该方式设置内容更加的灵活，可以使用DataUpdater<T>设置内容
            // public <T> void updateDataSerialized(String path, DataUpdater<T> updater)
            /*zkClient.writeData("/testCreatePersistent2", new DataUpdater<String>() {
                @Override
                public String update(String currentData) {
                    return "newdata4";
                }
            });

            Object data3 = zkClient.readData("/testCreatePersistent2");
            Assertions.assertEquals(data3, "newdata4", "xxxxxxxxx");*/
        }

        //public Stat writeDataReturnStat(final String path, Object datat, final int expectedVersion)
        @Test
        @DisplayName("testWriteDataReturnStat")
        void testWriteDataReturnStat() {
            Stat stat = new Stat();
            Object data = zkClient.readData("/testCreatePersistent2", stat);
            Stat stat1 = zkClient.writeDataReturnStat("/testCreatePersistent2", "newdata5", stat.getVersion());
            Assertions.assertEquals(stat1.getVersion() - stat.getVersion(), 1);
        }
    }

    @Order(5)
    @Nested
    @DisplayName("ZNodeDeleteTest")
    public class ZNodeDeleteTest extends ZkClientBaseTest {

    }


}
