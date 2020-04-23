package com.fta.websocketserver.websocket;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(MyHandler.class);

//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        super.userEventTriggered(ctx, evt);
//
//        if (evt instanceof IdleStateEvent) {
//
//            IdleStateEvent event = (IdleStateEvent) evt;
//            String channelId = ctx.channel().id().asShortText();
//
//            if (event.state().equals(IdleState.READER_IDLE)) {
//                //未进行读操作
//                logger.info("READER_IDLE " + channelId);
//                // 超时关闭channel
//                ctx.close();
//
//            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
//                logger.info("WRITER_IDLE " + channelId);
//            } else if (event.state().equals(IdleState.ALL_IDLE)) {
//                //未进行读写
//                logger.info("ALL_IDLE " + channelId);
//                TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(channelId);
//                ctx.channel().writeAndFlush(textWebSocketFrame);
//            }
//
//        }
//    }
}
