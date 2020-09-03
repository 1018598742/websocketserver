package com.fta.websocketserver.websocket;

import com.fta.websocketserver.utils.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class WebsocketServer {
    private static Logger log = Logger.getLogger(WebsocketServer.class);

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    int size = NettyConfig.GROUP.size();
                    log.info("-----------------------------连接数-----------------："+size);
                }
            },5000,30 * 1000);

            //启动订阅
//            JedisUtil.init();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new MyWebsocketInitializer());
            log.info("服务端开启等待客户端连接...");
            log.info("端口号："+Constants.PORT);
            Channel channel = bootstrap.bind(Constants.PORT).sync().channel();
            channel.closeFuture().sync();


        } catch (Exception e) {
            log.error("服务端启动失败", e);
        } finally {
            // 退出程序
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            log.info("服务端已关闭");
        }
    }
}
