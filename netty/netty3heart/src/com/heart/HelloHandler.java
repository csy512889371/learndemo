package com.heart;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateEvent;

public class HelloHandler extends SimpleChannelHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println(e.getMessage());
    }

    @Override
    public void handleUpstream(final ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof IdleStateEvent) {
            if (((IdleStateEvent) e).getState() == IdleState.ALL_IDLE) {
                System.out.println("提玩家下线");
                //关闭会话,踢玩家下线
                ChannelFuture write = ctx.getChannel().write("time out, you will close");
                write.addListener(new ChannelFutureListener() {

                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        ctx.getChannel().close();
                    }
                });
            }
        } else {
            super.handleUpstream(ctx, e);
        }
    }

}
