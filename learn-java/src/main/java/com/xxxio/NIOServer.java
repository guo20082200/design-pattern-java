package com.xxxio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 打开ServerSocketChannel通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置通道为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 将该通道对应的ServerSocket绑定到port端口
        serverSocketChannel.bind(new InetSocketAddress(8030));
        // 获得多路复用器Selector
        Selector selector = Selector.open();
        // 将ServerSocketChannel通道注册到Selector上，
        // 并为该通道设置OP_ACCEPT事件，等待接收客户端的请求
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动成功！");
 
        // 服务端不能停止，需要轮询selector来获取客户端的请求
        while (true) {
            // 当注册的事件到达时，方法会返回；否则,该方法会一直阻塞
            selector.select();
            // 获得selector中选中的项的迭代器，选中的项为注册的事件
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
            // 遍历SelectionKey中的事件，挨个处理
            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                // 取出SelectionKey后，从迭代器中移出，防止重复执行
                ite.remove();
                if (key.isAcceptable()) { // 获取到连接事件
                    ServerSocketChannel server = (ServerSocketChannel)key.channel();
                    // 获得和客户端连接的通道
                    SocketChannel channel = server.accept();
                    // 设置成非阻塞
                    channel.configureBlocking(false);
                    // 给客户端发送信息
                    channel.write(ByteBuffer.wrap("已连接成功".getBytes(StandardCharsets.UTF_8)));
                    // 给SocketChannel设置可读权限，以便从SocketChannel读取消息。
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) { // 获取到可读事件
                    // 得到可读事件的SocketChannel
                    SocketChannel channel = (SocketChannel)key.channel();
                    // 分配缓冲区
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    try {
                        // 将数据读取到缓冲区
                        channel.read(buffer);
                        // 从缓冲区取出数据
                        byte[] data = buffer.array();
                        String msg = new String(data);
                        System.out.println("服务端接收到信息：" + msg);
                        String returnMsg = msg + "消息已接收";
                        // 将需要返回给客户端的消息放入缓冲区
                        ByteBuffer outBuffer = ByteBuffer.wrap(returnMsg.getBytes(StandardCharsets.UTF_8));
                        // 将消息回送给客户端
                        channel.write(outBuffer);
                    } catch (IOException e) {
                        // 收到异常时，可能是客户端断开了，所以把通道关闭
                        channel.close();
                    }
                }
            }
        }
    }
}