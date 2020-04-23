package com.fta.websocketserver.websocket;


import com.fta.websocketserver.utils.Constants;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class MyWebsocketInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(Constants.HTTP_CODEC, new HttpServerCodec());
        ch.pipeline().addLast(Constants.AGGREGATOR, new HttpObjectAggregator(Constants.MAX_CONTENT_LENGTH));
        ch.pipeline().addLast(Constants.HTTP_CHUNKED, new ChunkedWriteHandler());
        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(60, 40, 30));
        ch.pipeline().addLast(Constants.HANDLER, new MyWebsocketHandler());
//        ch.pipeline().addLast("myhandler",new MyHandler());
    }
}
