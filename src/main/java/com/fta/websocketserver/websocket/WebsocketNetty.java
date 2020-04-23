package com.fta.websocketserver.websocket;

import com.fta.websocketserver.utils.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 入口
 */
public class WebsocketNetty {
    private final static Logger logger = LoggerFactory.getLogger(WebsocketNetty.class);

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            //启动订阅
            JedisUtil.init();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new MyWebsocketInitializer());
            logger.info("服务端开启等待客户端连接...");

            Channel channel = bootstrap.bind(Constants.PORT).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            logger.error("服务端启动失败", e);
        } finally {
            // 退出程序
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            logger.info("服务端已关闭");
        }
    }
}
