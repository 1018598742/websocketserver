package com.fta.websocketserver.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * 初始化连接时候的各个组件
 */
public class MyWebsocktChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
//        SSLContext sslContext = SslUtil.createSSLContext("JKS", "D:\\wss.jks", "netty123");
        SSLContext sslContext = SslUtil.createSSLContext("JKS", "./sign/skeystore.jks", "123456");
//        SSLContext sslContext = SslUtil.createSSLContext("JKS", "E:\\IntellijIdeaWorkplace\\testwebsocketserver\\testwebsocketserver\\sign\\skeystore.jks", "123456");
        //SSLEngine 此类允许使用ssl安全套接层协议进行安全通信
        SSLEngine engine = sslContext.createSSLEngine();
        engine.setUseClientMode(false);
//        channel.pipeline().addLast(new SslHandler(engine));

        channel.pipeline().addLast("http-codec", new HttpServerCodec());
        channel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        channel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());

//        channel.pipeline().addLast("idleStateHandler", new IdleStateHandler(25, 15, 10));
        //需要隔一段时间发送消息
//        channel.pipeline().addLast("idleStateHandler", new IdleStateHandler(30, 20, 10));
//        channel.pipeline().addLast("myHandler", new MyHandler());

        channel.pipeline().addLast("handler", new MyWebsocketHandler());

    }
}
