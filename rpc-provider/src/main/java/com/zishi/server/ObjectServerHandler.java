package com.zishi.server;

import com.zishi.ent.ZishiRpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ObjectServerHandler extends ChannelInboundHandlerAdapter {



    /**
     * 本方法用于读取客户端发送的信息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("SimpleServerHandler.channelRead");

        System.out.println(msg.getClass());

        ZishiRpcResponse response = new ZishiRpcResponse();
        ctx.writeAndFlush(response);
    }

    /**
     * 本方法用作处理异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 信息获取完毕后操作
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}