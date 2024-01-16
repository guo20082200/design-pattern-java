package com.zishi.zk.zkclient;


import org.I0Itec.zkclient.IZkConnection;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 为什么使用ZkClient客户端
 * ZkClient在原生的Zookeeper API接口上进行了包装，更加简单易用。ZkClient内部还实现了Session超时重连、Watcher反复注册等功能，使得这些操作对开发透明，提高了开发的效率。
 * <p>
 * ZkClient主要解决了原生api的两个大问题：
 * <p>
 * ZkClient解决了watcher的一次性注册问题，将znode的事件重新定义为子节点的变化、数据的变化、连接状态的变化三类，有ZkClient统一将watcher的WatchedEvent转换到以上三种情况中去处理，watcher执行后重新读取数据的同时，在注册新的相同的watcher。
 * <p>
 * ZkClient将Zookeeper的watcher机制转化为一种更加容易理解的订阅形式，并且这种关系是可以保持的，而非一次性的。也就是说子节点的变化、数据的变化、状态的变化是可以订阅的。当watcher使用完后，zkClient会自动增加一个相同的watcher。
 * <p>
 * 在session loss和session expire时自动创建新的ZooKeeper实例进行重连
 * <p>
 * 101tec这个zookeeper客户端主要有以下特性：
 * <p>
 * 提供了zookeeper重连的特性——能够在断链的时候,重新建立连接,无论session失效与否.
 * 持久的event监听器机制—— ZKClient框架将事件重新定义分为了stateChanged、znodeChanged、dataChanged三种情况，用户可以注册这三种情况下的监听器（znodeChanged和dataChanged和路径有关），而不是注册Watcher。
 * zookeeper异常处理——-zookeeper中繁多的Exception,以及每个Exception所需要关注的事情各有不同，I0Itec简单的做了封装。
 * data序列化——简单的data序列化.(Serialzer/Deserialzer)
 * 有默认的领导选举机制
 * 注意：使用101tec-zkClient，有一个方法还需要完善，create()方法，使用该方法创建节点时，如果节点已经存在，仍然抛出NodeExistException，需要自己手动去判断是否存在某个节点，这一步其实可以优化的。
 * <p>
 * <p>
 * 创建客户端得方式：
 * <p>
 * public ZkClient(String serverstring)
 * public ZkClient(String zkServers, int connectionTimeout)
 * public ZkClient(String zkServers, int sessionTimeout, int connectionTimeout)
 * public ZkClient(String zkServers, int sessionTimeout, int connectionTimeout, ZkSerializer zkSerializer)
 * public ZkClient(IZkConnection connection)
 * public ZkClient(IZkConnection connection, int connectionTimeout)
 * public ZkClient(IZkConnection zkConnection, int connectionTimeout, ZkSerializer zkSerializer)
 */
public class ZkClientDemo01Test {

    private static final String ADDR = "localhost:2181";
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
