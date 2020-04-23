package com.fta.websocketserver.redispub;


import com.fta.websocketserver.websocket.JedisUtil;
import com.fta.websocketserver.websocket.NettyConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.DefaultChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

import java.util.Date;

/**
 */
public class RedisMsgPubSubListener extends JedisPubSub {

    private static Logger log = LoggerFactory.getLogger(RedisMsgPubSubListener.class);

    @Override
    public void onMessage(String msgChannel, String iMChannelId) {
        log.info("RedisMsgPubSubListener-onMessage-iMChannelId="+iMChannelId+"   msgChannel="+msgChannel);
        if (StringUtil.isNullOrEmpty(iMChannelId)){
            return;
        }

        // 避免 如果不是在本机，则丢弃  避免 压力透彻到 redis
//        String isExists = NettyConfig.LOCALCHANNELMAP.get(iMChannelId);
//        if (StringUtil.isNullOrEmpty(isExists)){
//            // 离线则丢弃，不推送
//            return;
//        }

        String msg = JedisUtil.get(iMChannelId);
        if (StringUtil.isNullOrEmpty(msg)){
            return;
        }


        ChannelId id = JedisUtil.get(iMChannelId+"Id", DefaultChannelId.class);
        if (id == null){
            return;
        }

        log.info("iMChannelId:"+iMChannelId+"  msg:"+msg);
//        Channel channel = NettyConfig.GROUP.find(id);
//        if (channel != null){
//            String responseStr = new Date().toString()  + channel.id() +  " ===>>> " + msg;
//            TextWebSocketFrame tws = new TextWebSocketFrame(responseStr);
//            channel.writeAndFlush(tws);
//
//            //如果推送完成，在清理到 redis中的 消息
//            JedisUtil.del(iMChannelId);
//        }

        String responseStr = new Date().toString()  + iMChannelId +  " ===>>> " + msg;
        TextWebSocketFrame tws = new TextWebSocketFrame(responseStr);
        NettyConfig.GROUP.writeAndFlush(tws);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        log.info("channel:" + channel + "is been subscribed:" + subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        log.info("channel:" + channel + "is been unsubscribed:" + subscribedChannels);
    }

}
