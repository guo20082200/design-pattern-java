package com.zishi.server;


import com.zishi.codec.NettyDecoder;
import com.zishi.codec.NettyEncoder;
import com.zishi.codec.RpcMessageDecoder;
import com.zishi.codec.RpcMessageEncoder;
import com.zishi.config.CustomShutdownHook;
import com.zishi.ent.ZishiRpcRequest;
import com.zishi.ent.ZishiRpcResponse;
import com.zishi.util.ThreadPoolFactoryUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * 服务端生产者对象
 * This is an netty Server
 * Netty中，通讯的双方建立连接后，会把数据按照ByteBuf的方式进行传输，
 * 例如http协议中，就是通过HttpRequestDecoder对ByteBuf数据流进行处理，转换成http的对象。
 */
@Slf4j
public class NettyServer {
    /**
     * 服务端口
     */
    public static final int PORT = 9999;

    /**
     * 开启服务的方法
     */
    public void StartNetty() throws UnknownHostException {

        CustomShutdownHook.getCustomShutdownHook().clearAll();
        String host = InetAddress.getLocalHost().getHostAddress();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        DefaultEventExecutorGroup serviceHandlerGroup = new DefaultEventExecutorGroup(
                Runtime.getRuntime().availableProcessors() * 2,
                ThreadPoolFactoryUtil.createThreadFactory("service-handler-group", false)
        );

        /**创建两个EventLoop的组，EventLoop 这个相当于一个处理线程，
         是Netty接收请求和处理IO请求的线程。不理解的话可以百度NIO图解*/
        /* 
        相关资料：NioEventLoopGroup是一个处理I/O操作的多线程事件循环。
        Netty为不同类型的传输提供了各种EventLoopGroup实现。
        在本例中，我们正在实现一个服务器端应用程序，因此将使用两个NioEventLoopGroup。
        第一个，通常称为“boss”，接受传入的连接。
        第二个，通常称为“worker”，当boss接受连接并注册被接受的连接到worker时，处理被接受连接的流量。
        使用了多少线程以及如何将它们映射到创建的通道取决于EventLoopGroup实现，甚至可以通过构造函数进行配置。
        */
        EventLoopGroup acceptor = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            /*//1、创建启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            //2、配置启动参数等
            *//**设置循环线程组，前者用于处理客户端连接事件，后者用于处理网络IO(server使用两个参数这个)
             *public ServerBootstrap group(EventLoopGroup group)
             *public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup)
             *//*
            bootstrap.group(acceptor, worker);
            *//**设置选项
             * 参数：Socket的标准参数（key，value），可自行百度
             * eg:
             * bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
             *bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
             * *//*
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            *//*bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);*//*

            //用于构造socketchannel工厂
            bootstrap.channel(NioServerSocketChannel.class);
            *//**
             * 传入自定义客户端Handle（服务端在这里搞事情）
             *//*
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 注册handler
                    ch.pipeline().addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                    ch.pipeline().addLast(new NettyDecoder(ZishiRpcRequest.class));
                    ch.pipeline().addLast(new NettyEncoder(ZishiRpcResponse.class));
                    ch.pipeline().addLast(new ObjectServerHandler());
                }
            });

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = bootstrap.bind(PORT).sync();

            // 等待服务器 socket 关闭 。
            f.channel().closeFuture().sync();*/

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // TCP默认开启了 Nagle 算法，该算法的作用是尽可能的发送大数据快，减少网络传输。TCP_NODELAY 参数的作用就是控制是否启用 Nagle 算法。
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 是否开启 TCP 底层心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 当客户端第一次进行请求的时候才会进行初始化
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 30 秒之内没有收到客户端请求的话就关闭连接
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            pipeline.addLast(new RpcMessageEncoder());
                            pipeline.addLast(new RpcMessageDecoder());
                            pipeline.addLast(serviceHandlerGroup, new ObjectServerHandler());
                        }
                    });

            // 绑定端口，同步等待绑定成功
            ChannelFuture f = b.bind(host, PORT).sync();
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            acceptor.shutdownGracefully();
            worker.shutdownGracefully();

            log.error("shutdown bossGroup and workerGroup");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            serviceHandlerGroup.shutdownGracefully();
        }

    }


    public static void main(String[] args) throws UnknownHostException {
        new NettyServer().StartNetty();
    }
}