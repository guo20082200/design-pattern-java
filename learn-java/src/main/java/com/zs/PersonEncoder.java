package com.zs;

import com.zs.ByteBufToBytes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 序列化
 * 将object转换成Byte[]
 * @author wilson
 *
 */
public class PersonEncoder extends MessageToByteEncoder<Person> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {
        //工具类：将object转换为byte[]
        byte[] datas = ByteObjConverter.objectToByte(msg);
        out.writeBytes(datas);
        ctx.flush();
    }
}