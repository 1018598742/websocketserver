package com.fta.websocketserver.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.io.InputStream;

/**
 * 初始化连接时候的各个组件
 */
public class MyWebsocktChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("keystore/skeystore.jks");
        InputStream inputStream = classPathResource.getInputStream();
        SSLContext sslContext = SslUtil.createSSLContext("JKS", inputStream, "123456");
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
        channel.pipeline().addLast("idleStateHandler", new IdleStateHandler(60, 40, 30));
        channel.pipeline().addLast("myHandler", new MyHandler());

        channel.pipeline().addLast("handler", new MyWebsocketHandler());

    }
}
