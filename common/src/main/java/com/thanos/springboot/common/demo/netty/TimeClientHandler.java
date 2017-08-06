package com.thanos.springboot.common.demo.netty;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author solarknight created on 2017/6/22 16:01
 * @version 1.0
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf m = (ByteBuf) msg; // (1)
    try {
      long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
      System.out.println(new Date(currentTimeMillis));
      ctx.close();
    } finally {
      m.release();
    }
  }
}
