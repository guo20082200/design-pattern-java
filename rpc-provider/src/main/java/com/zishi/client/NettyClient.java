package com.zishi.client;
import com.zishi.codec.NettyDecoder;
import com.zishi.codec.NettyEncoder;
import com.zishi.codec.RpcMessageDecoder;
import com.zishi.codec.RpcMessageEncoder;
import com.zishi.ent.ZishiRpcRequest;
import com.zishi.ent.ZishiRpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 客户端消费者对象
 * This is an netty Client
 */
public class NettyClient {
    public void connect(String host, int port) throws Exception {
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            /**
             *EventLoop的组
             */
            b.group(worker);
            /**
             * 用于构造socketchannel工厂
             */
            b.channel(NioSocketChannel.class);
            /**设置选项
             * 参数：Socket的标准参数（key，value），可自行百度
               保持呼吸，不要断气！
             * */
            b.option(ChannelOption.SO_KEEPALIVE, true);
            /**
             * 自定义客户端Handle（客户端在这里搞事情）
             */
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 注册handler
                    // .addLast(new IdleStateHandler(0,0,Beat.BEAT_INTERVAL, TimeUnit.SECONDS))
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                    /*ch.pipeline().addLast(new NettyDecoder(ZishiRpcRequest.class));
                    ch.pipeline().addLast(new NettyEncoder(ZishiRpcResponse.class));*/
                    pipeline.addLast(new RpcMessageEncoder());
                    pipeline.addLast(new RpcMessageDecoder());
                    pipeline.addLast(new ObjectClientHandler());
                }
            });
            /** 开启客户端监听*/
            ChannelFuture f = b.connect(host, port).sync();
            /**等待数据直到客户端关闭*/
            f.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
        }
    }
 
    public static void main(String[] args) throws Exception {
        NettyClient client=new NettyClient();
        client.connect("127.0.0.1", 9999);
 
    }
}