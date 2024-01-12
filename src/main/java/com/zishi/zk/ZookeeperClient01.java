package com.zishi.zk;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 * 原生的zookeeper客户端（官方）
 * 1.连接是异步的，使用时需要注意，增加watcher，监听事件如果为SyncConnected，那么才做其他的操作。（可以使用CountDownLatch或者其他栅栏控制--并发编程）
 * 2.监听事件是一次性的，如果操作多次需要注册多次
 * <p>
 * api：https://zookeeper.apache.org/doc/r3.6.1/apidocs/zookeeper-server/index.html
 * <p/>
 *
 * @author smilehappiness
 * @Date 2020/6/20 16:09
 */
public class ZookeeperClient01 {

    /**
     * 客户端连接地址
     */
    private static final String ZK_ADDRESS = "ip:2181";
    /**
     * 客户端根节点
     */
    private static final String ROOT_NODE = "/root";
    /**
     * 客户端子节点
     */
    private static final String ROOT_NODE_CHILDREN = "/root/user";
    /**
     * 倒计数器，倒数1个
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    /**
     * ZooKeeper对象
     */
    private static ZooKeeper zookeeper = null;

    /**
     * <p>
     * zookeeper客户端使用
     * <p>
     * 注意：不要使用debug进行断点测试，否则可能会报错（
     * 如：org.apache.zookeeper.KeeperException$ConnectionLossException: KeeperErrorCode = ConnectionLoss for /root）
     * <p/>
     *
     * @param args
     * @return void
     * @Date 2020/6/20 15:42
     */
    public static void main(String[] args) throws Exception {

        //1、初始化zookeeper，创建zookeeper客户端对象
        initConnect(ZK_ADDRESS, 5000);

        //2、创建节点
        createNode(ROOT_NODE, "root data1");
        createNode(ROOT_NODE + "/home", "home data1");

        //递归创建节点（递归每个节点，并赋相同的值，这种场景用的不是很多）
        createNodeRecursion(ROOT_NODE_CHILDREN, "recursion data1");

        //3、查询节点
        queryNode(ROOT_NODE);

        //4、修改节点
        updateNodeData(ROOT_NODE, "nice");

        //5、单个节点删除（注意：如果节点下有子节点，不能删除--NotEmptyException: KeeperErrorCode = Directory not empty for /root）
        //zookeeper客户端的api里，暂时没找到可以直接删除当前节点以及子节点的方法
        //deleteNode(ROOT_NODE);
        //递归删除节点
        deleteRecursion(ROOT_NODE_CHILDREN);
    }

    /**
     * <p>
     * 初始化zookeeper，创建zookeeper客户端对象
     * <p/>
     *
     * @param connectAddress
     * @param sessionTimeout
     * @return void
     * @Date 2020/6/20 13:22
     */
    private static void initConnect(String connectAddress, int sessionTimeout) {
        try {
            //创建zookeeper客户端对象
            //zookeeper = new ZooKeeper(connectAddress, sessionTimeout, null);
            //以上这种方式，由于zookeeper连接是异步的，如果new ZooKeeper(connectStr, sessionTimeout, null)完之后马上使用，有可能会报错。

            //解决办法：增加watcher监听事件，如果为SyncConnected，那么才做其他的操作。（这里利用CountDownLatch倒数器来控制）
            zookeeper = new ZooKeeper(connectAddress, sessionTimeout, watchedEvent -> {
                //获取监听事件的状态
                Watcher.Event.KeeperState state = watchedEvent.getState();

                //获取监听事件类型
                Watcher.Event.EventType type = watchedEvent.getType();

                //如果已经建立上了连接
                if (Watcher.Event.KeeperState.SyncConnected == state) {
                    if (Watcher.Event.EventType.None == type) {
                        System.out.println("zookeeper连接成功......");
                        countDownLatch.countDown();
                    }
                }

                if (Watcher.Event.EventType.NodeCreated == type) {
                    System.out.println("zookeeper有新节点【" + watchedEvent.getPath() + "】创建!");
                }
                if (Watcher.Event.EventType.NodeDataChanged == type) {
                    System.out.println("zookeeper有节点【" + watchedEvent.getPath() + "】数据变化!");
                }
                if (Watcher.Event.EventType.NodeDeleted == type) {
                    System.out.println("zookeeper有节点【" + watchedEvent.getPath() + "】被删除!");
                }
                if (Watcher.Event.EventType.NodeChildrenChanged == type) {
                    System.out.println("zookeeper有子节点【" + watchedEvent.getPath() + "】变化!");
                }
            });

            //倒计数器没有倒数完成，不能执行下面的代码，因为需要等zookeeper连上了，才可以进行node的操作，否则可能会报错
            countDownLatch.await();

            System.out.println("init connect success：" + zookeeper);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * 根据指定路径，创建zNode节点，并赋值数据
     * <p/>
     *
     * @param nodePath
     * @param data
     * @return void
     * @Date 2020/6/20 13:58
     */
    private static void createNode(String nodePath, String data) throws KeeperException, InterruptedException {
        if (StringUtils.isEmpty(nodePath)) {
            System.out.println("节点【" + nodePath + "】不能为空");
            return;
        }

        //对节点是否存在进行判断，否则会报错：【NodeExistsException: KeeperErrorCode = NodeExists for /root】
        Stat exists = zookeeper.exists(nodePath, true);
        if (null != exists) {
            System.out.println("节点【" + nodePath + "】已存在，不能新增");
            return;
        }

        String result = zookeeper.create(nodePath, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("create:" + "【" + nodePath + "-->" + data + "】，result:" + result);
    }

    /**
     * <p>
     * 根据指定路径，递归创建zNode节点，并赋值数据
     * <p/>
     *
     * @param nodePath
     * @param data
     * @return void
     * @Date 2020/6/20 14:28
     */
    private static void createNodeRecursion(String nodePath, String data) throws KeeperException, InterruptedException {
        if (StringUtils.isEmpty(nodePath)) {
            System.out.println("节点【" + nodePath + "】不能为空");
            return;
        }

        String paths[] = nodePath.substring(1).split("/");
        for (int i = 0; i < paths.length; i++) {
            String childPath = "";
            for (int j = 0; j <= i; j++) {
                childPath += "/" + paths[j];
            }
            createNode(childPath, data);
        }
    }

    /**
     * <p>
     * 查询节点
     * <p/>
     *
     * @param nodePath
     * @return void
     * @Date 2020/6/20 15:12
     */
    private static void queryNode(String nodePath) throws KeeperException, InterruptedException {
        System.out.println("--------------------华丽的分割线-------------------------");

        byte[] bytes = zookeeper.getData(nodePath, false, null);
        System.out.println(new String(bytes));

        Stat stat = new Stat();
        byte[] data = zookeeper.getData(nodePath, true, stat);
        System.out.println("queryNode:" + "【" + nodePath + "】，result：" + new String(data) + "，stat：" + stat);
    }

    /**
     * <p>
     * 更新指定节点的数据
     * <p/>
     *
     * @param nodePath
     * @param data
     * @return void
     * @Date 2020/6/20 16:01
     */
    private static void updateNodeData(String nodePath, String data) throws KeeperException, InterruptedException {
        //version = -1代表不指定版本
        Stat stat = zookeeper.setData(nodePath, data.getBytes(), -1);
        System.out.println("setData:" + "【" + nodePath + "】,stat:" + stat);
    }

    /**
     * <p>
     * 根据某个节点，删除节点
     * <p/>
     *
     * @param nodePath
     * @return void
     * @Date 2020/6/20 15:28
     */
    private static void deleteNode(String nodePath) throws KeeperException, InterruptedException {
        System.out.println("--------------------华丽的分割线-------------------------");

        Stat exists = zookeeper.exists(nodePath, true);
        if (null == exists) {
            System.out.println(nodePath + "不存在，请核实后在进行相关操作！");
            return;
        }

        zookeeper.delete(nodePath, -1);//version：-1表示删除节点时，不指定版本
        System.out.println("delete node:" + "【" + nodePath + "】");
    }

    /**
     * <p>
     * 根据某个路径，递归删除节点（该方式会删除父节点）
     * <p/>
     *
     * @param nodePath
     * @return void
     * @Date 2020/6/20 15:29
     */
    private static void deleteRecursion(String nodePath) throws KeeperException, InterruptedException {
        System.out.println("--------------------华丽的分割线-------------------------");

        Stat exists = zookeeper.exists(nodePath, true);
        if (null == exists) {
            System.out.println(nodePath + "不存在，请核实后在进行相关操作！");
            return;
        }

        //获取当前nodePath下，子节点的数据
        List<String> list = zookeeper.getChildren(nodePath, true);
        if (list.isEmpty()) {
            deleteNode(nodePath);

            String parentPath = nodePath.substring(0, nodePath.lastIndexOf("/"));
            System.out.println("parentPath=" + parentPath);
            //如果当前节点存在父节点，连带的删除父节点，以及父节点下所有的子节点
            if (StringUtils.isNotBlank(parentPath)) {
                deleteRecursion(parentPath);
            }
        } else {
            for (String child : list) {
                deleteRecursion(nodePath + "/" + child);
            }
        }
    }

}
