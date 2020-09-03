package com.fta.websocketserver.websocket;

import com.alibaba.fastjson.JSON;
import com.fta.testwebsocket.MD5Utils;
import com.fta.websocketserver.bean.ChiledBean;
import com.fta.websocketserver.bean.UpgradeBaseResp;
import com.fta.websocketserver.utils.ConstType;
import com.fta.websocketserver.utils.Constants;
import com.fta.websocketserver.utils.ParseServerInfoHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;


/**
 * @program: Netty-WebSocket
 * @description: 接收处理并响应客户端WebSocket请求的核心业务处理类
 **/
@ChannelHandler.Sharable
public class MyWebsocketHandler extends SimpleChannelInboundHandler<Object> {

    private final static Logger log = Logger.getLogger(MyWebsocketHandler.class);

    private WebSocketServerHandshaker handshaker;

    /**
     * 服务端处理客户端WebSocket请求的核心方法
     *
     * @param ctx ctx
     * @param msg msg
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        // 处理客户端向服务端发起http握手请求的业务
        if (msg instanceof FullHttpRequest) {
//            log.info("is FullHttpRequest");
            handHttpRequest(ctx, (FullHttpRequest) msg);
        }
        // 处理websocket连接
        else if (msg instanceof WebSocketFrame) {
//            log.info("is WebSocketFrame");
            handWebsocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    /**
     * 处理客户端与服务端之间的websocket业务
     *
     * @param ctx   ctx
     * @param frame frame
     */
    private void handWebsocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否是关闭websocket的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), ((CloseWebSocketFrame) frame).retain());
            log.info("接收到关闭websocket的指令");
        }

        // 判断是否是ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            log.info("接收到ping消息");
            return;
        }

        if (frame instanceof BinaryWebSocketFrame) {
            log.info("服务器接收到二进制消息.");
            ByteBuf content = frame.content();
            content.markReaderIndex();
//            int flag = content.readInt();
            short flag = content.readShort();
            if (flag == ConstType.CLIENTHEART) {
                log.info("服务端发消息了！");
            }
            log.info("标志位:" + flag);
            int length = content.readInt();
            log.info("内容长度：" + length);
            //验证信息
            byte[] vertify = new byte[32];
            content.readBytes(vertify);
            String vertifyInfo = new String(vertify);
            log.info("验证信息：" + vertifyInfo);
            //读数据部分
            byte[] data = new byte[length];
            content.readBytes(data);
            String s = new String(data);
            log.info("内容：" + s);
            String s1 = new MD5Utils().stringToMD5(s);
            if (s1 != null && s1.equals(vertifyInfo)) {
                log.info("有效！");
            }

            content.resetReaderIndex();

//            ByteBuf byteBuf = Unpooled.directBuffer(frame.content().capacity());
//            byteBuf.writeBytes(frame.content());
            UpgradeBaseResp<ChiledBean> chiledBeanUpgradeBaseResp = new UpgradeBaseResp<>();
            chiledBeanUpgradeBaseResp.setCode("00000");
            chiledBeanUpgradeBaseResp.setMsg("00描述");
            ChiledBean chiledBean = new ChiledBean("小明", "描述");
            chiledBeanUpgradeBaseResp.setData(chiledBean);
            String json = JSON.toJSONString(chiledBeanUpgradeBaseResp);
            log.info("需要发送给客户端的信息："+json);
            ByteBuf byteBuf = ParseServerInfoHelper.INSTANCE.generByteString(flag, json);
            if (byteBuf != null) {
                ctx.writeAndFlush(new BinaryWebSocketFrame(byteBuf));
            } else {
                log.info("要返回的信息为null");
            }
            return;
        }

        if (frame instanceof TextWebSocketFrame) {
            String requestStr = ((TextWebSocketFrame) frame).text();
//            log.info("服务端收到客户端的消息: " + requestStr);
        }

        // 判断是否是二进制消息，如果是二进制消息，则抛出异常
//        if (!(frame instanceof TextWebSocketFrame)) {
//            log.error("目前不支持二进制消息");
//            throw new UnsupportedOperationException("【" + this.getClass().getName() + "】不支持的消息");
//        }

        // 获取客户端向服务端发送的消息
//        String requestStr = ((TextWebSocketFrame) frame).text();
//        log.info("服务端收到客户端的消息: " + requestStr);


        // 那个客户端发来的，继续返回给那个客户端
        //Channel channel = NettyConfig.GROUP.find(ctx.channel().id());
        //channel.writeAndFlush(tws);

        // 发布到redis 订阅列表中，进行广播
//        String keychannel = ctx.channel().id().asLongText();
//        ChannelId id = ctx.channel().id();
//        JedisUtil.set(keychannel, requestStr);
//        JedisUtil.set(keychannel + "Id", id);
//        JedisUtil.pushMsg(keychannel);


        // 返回应答消息
//        String responseStr = new Date().toString()  + ctx.channel().id() +  " ===>>> " + requestStr;
//        TextWebSocketFrame tws = new TextWebSocketFrame(responseStr);
        // 群发，服务端向每个连接上来的客户端群发消息
        //NettyConfig.GROUP.writeAndFlush(tws);

//        log.info("群发消息完成. 群发的消息为: {}", responseStr);
    }

    /**
     * 处理客户端向服务端发起http握手请求的业务
     *
     * @param ctx     ctx
     * @param request request
     */
    private void handHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {

        String uri = request.uri();
        log.info("请求地址：" + uri);

        HttpHeaders headers = request.headers();

        String name = headers.get("name");
        log.info("请求头name：" + name);
        String cert = headers.get("cert");
        log.info("请求头cert:" + cert);
        String ipAddress = headers.get("X-Real_IP");
        log.info("ipAddress:" + ipAddress);


        String upgrade = headers.get(Constants.UPGRADE_STR);

        // 非websocket的http握手请求处理
        if (!request.decoderResult().isSuccess() || !Constants.WEBSOCKET_STR.equals(upgrade)) {
            sendHttpResponse(ctx, request,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            log.warn("非websocket的http握手请求");
            return;
        }

        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(Constants.WEB_SOCKET_URL, null, false);
        handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) {
            // 响应不支持的请求
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            log.warn("不支持的请求");
        } else {
            handshaker.handshake(ctx.channel(), request);
            log.info("正常处理");
        }

    }

    /**
     * 服务端主动向客户端发送消息
     *
     * @param ctx      ctx
     * @param request  request
     * @param response response
     */
    private void sendHttpResponse(ChannelHandlerContext ctx,
                                  FullHttpRequest request,
                                  DefaultFullHttpResponse response) {
        // 不成功的响应
        if (response.status().code() != Constants.OK_CODE) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            log.warn("不成功的响应");
        }

        // 服务端向客户端发送数据
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(response);
        if (!HttpUtil.isKeepAlive(request) ||
                response.status().code() != Constants.OK_CODE) {
            // 如果是非Keep-Alive，或不成功都关闭连接
            channelFuture.addListener(ChannelFutureListener.CLOSE);
            log.info("websocket连接关闭");
        }
    }

    /**
     * 客户端与服务端创建连接的时候调用
     *
     * @param ctx ctx
     * @throws Exception Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 将channel添加到channel group中
        NettyConfig.GROUP.add(ctx.channel());
        NettyConfig.LOCALCHANNELMAP.put(ctx.channel().id().asLongText(), "1");//存在，并且在线
        log.info("客户端与服务端连接开启...");
    }

    /**
     * 客户端与服务端断开连接的时候调用
     *
     * @param ctx ctx
     * @throws Exception Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 从channel group中移除这个channel
        NettyConfig.GROUP.remove(ctx.channel());
        NettyConfig.LOCALCHANNELMAP.remove(ctx.channel().id().asLongText());// 不存在，离线
        log.info("客户端与服务端关闭连接...");
    }

    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     *
     * @param ctx ctx
     * @throws Exception Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 清空数据
        ctx.flush();
    }

    /**
     * 工程出现异常的时候调用
     *
     * @param ctx   ctx
     * @param cause cause
     * @throws Exception Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 打印异常堆栈
//        cause.printStackTrace();
        // 主动关闭连接
        ctx.close();
        log.error("WebSocket连接异常:" + cause.toString());
    }

    /**
     * 心跳方法
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
//        if (evt instanceof IdleStateEvent) {
//
//            IdleStateEvent event = (IdleStateEvent) evt;
//            String channelId = ctx.channel().id().asShortText();
//
//            if (event.state().equals(IdleState.READER_IDLE)) {
//                //未进行读操作
//                log.info("READER_IDLE " + channelId);
//                // 超时关闭channel
//                ctx.close();
//
//            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
////                log.info("WRITER_IDLE " + channelId);
//            } else if (event.state().equals(IdleState.ALL_IDLE)) {
//                //未进行读写
////                log.info("ALL_IDLE " + channelId);
//                TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(channelId);
//                ctx.channel().writeAndFlush(textWebSocketFrame);
//            }
//
//        }
    }
}