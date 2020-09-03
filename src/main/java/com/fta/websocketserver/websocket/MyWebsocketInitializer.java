package com.fta.websocketserver.websocket;


import com.fta.websocketserver.utils.Constants;
import com.fta.websocketserver.utils.SslUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MyWebsocketInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {


//        try {
//            ClassPathResource classPathResource = new ClassPathResource("keystore/tomcat.keystore");
//            InputStream inputStream = classPathResource.getInputStream();
//            SSLContext sslContext = SslUtil.createSSLContext("JKS", inputStream, "123456");
//            //SSLEngine 此类允许使用ssl安全套接层协议进行安全通信
//            SSLEngine engine = sslContext.createSSLEngine();
//            engine.setUseClientMode(false);
//            //false为单向认证，true为双向认证
//            engine.setNeedClientAuth(true);
//            ch.pipeline().addLast(new SslHandler(engine));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        ch.pipeline().addLast(Constants.HTTP_CODEC, new HttpServerCodec());
        ch.pipeline().addLast(Constants.AGGREGATOR, new HttpObjectAggregator(Constants.MAX_CONTENT_LENGTH));
        // 服务器端向外暴露的 web socket 端点，当客户端传递比较大的对象时，maxFrameSize参数的值需要调大
        //若连接地址是ws://192.168.0.76:8989/chat 完全匹配。 MyWebsocketHandler 中就不会走 channelRead0 的 FullHttpRequest
//        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/chat", null, true, 10485760));
        ch.pipeline().addLast(Constants.HTTP_CHUNKED, new ChunkedWriteHandler());
        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(60, 40, 30));
        ch.pipeline().addLast(Constants.HANDLER, new MyWebsocketHandler());
    }
}
