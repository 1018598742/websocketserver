package com.fta.websocketserver.websocket;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: Netty-WebSocket
 * @description: 工程的全局配置类
 **/
public class NettyConfig {

    /**
     * 存储每一个客户端接入进来时的channel对象
     */
    public final static ChannelGroup GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 本地存储一份map <channel, 1>
     */
    public final static Map<String,String> LOCALCHANNELMAP = new ConcurrentHashMap<>();

    /**
     * 本地存储一份map <tokey, channel>  送达到key，channel
     */
    public final static Map<String, List<String>> LOCALCHANNELLISTMAP = new ConcurrentHashMap<>();

}