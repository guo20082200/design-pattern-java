package com.zishi.client;


import com.zishi.constants.RpcConstants;
import com.zishi.ent.RpcMessage;
import com.zishi.ent.ZishiRpcRequest;
import com.zishi.enums.CompressTypeEnum;
import com.zishi.enums.SerializationTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * 发送rpc的参数信息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("SimpleClientHandler.channelRead");
        System.out.println(msg.getClass());
        ZishiRpcRequest request = new ZishiRpcRequest();
        ctx.writeAndFlush(request);
    }

    /**
     * 本方法用于处理异常
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

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                log.info("write idle happen [{}]", ctx.channel().remoteAddress());
                //Channel channel = nettyRpcClient.getChannel((InetSocketAddress) ctx.channel().remoteAddress());
                RpcMessage rpcMessage = new RpcMessage();
                rpcMessage.setCodec(SerializationTypeEnum.PROTOSTUFF.getCode());
                rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
                rpcMessage.setMessageType(RpcConstants.HEARTBEAT_REQUEST_TYPE);
                rpcMessage.setData(RpcConstants.PING);
                //channel.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }


    /**
     * 本方法用于向服务端发送信息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*String msg = "hello Server!";
        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
        encoded.writeBytes(msg.getBytes());
        ctx.write(encoded);
        ctx.flush();*/

        /*ZishiRpcRequest request;
        for (int i = 0; i < 100; i++) {
            request = new ZishiRpcRequest();
            request.setAccessToken("aaaaa");
            request.setClassName("erfaera" + i);
            ctx.writeAndFlush(request.toString().getBytes());
            System.out.println("...");
        }*/
        RpcMessage message = new RpcMessage();
        message.setData("aaaaaaaaaaaaaaaa");
        ctx.writeAndFlush(message);
    }
}