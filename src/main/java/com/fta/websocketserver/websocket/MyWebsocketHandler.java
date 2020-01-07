package com.fta.websocketserver.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyWebsocketHandler extends SimpleChannelInboundHandler<Object> {

    private final static Logger logger = LoggerFactory.getLogger(MyWebsocketHandler.class);

    private WebSocketServerHandshaker webSocketServerHandshaker;

    //    private static final String WEBSOCKET_URL = "wss://localhost:8899/websocket";
//    private static final String WEBSOCKET_URL = "wss://localhost:8889/websocket";
    private static final String WEBSOCKET_URL = "ws://localhost:8889/websocket";

    /**
     * 处理请求的核心方法
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        logger.info("messageReceived  处理握手请求的业务");
        //处理握手请求的业务
        if (msg instanceof FullHttpMessage) {
            logger.info("messageReceived FullHttpMessage");
            handHttpRequest(channelHandlerContext, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {//处理websocket连接业务
            logger.info("messageReceived WebSocketFrame");
            handleWebsocketFrame(channelHandlerContext, (WebSocketFrame) msg);
            if ("anzhuo".equals(channelHandlerContext.attr(AttributeKey.valueOf("type")).get())) {

            }
        } else {
            logger.info("other received");
        }

    }

    private void handleWebsocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //是否是关闭指令
        if (frame instanceof CloseWebSocketFrame) {
            webSocketServerHandshaker.close(ctx.channel(), (CloseWebSocketFrame) frame);
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if (!(frame instanceof TextWebSocketFrame)) {
            logger.info("暂不支持二进制消息！");
            throw new RuntimeException("【" + this.getClass().getName() + "】不支持消息");
        }

        //返回应答消息
        String requestMsg = ((TextWebSocketFrame) frame).text();
        logger.info("服务端" + ctx.channel().id().asShortText() + "管道收到客户端的消息：" + requestMsg);
        if ("1111111111".equals(requestMsg)) {
            ChannelGroup channelGroup = NettyConfig.channelGroup;
            channelGroup.remove(ctx.channel());
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(requestMsg);
            channelGroup.writeAndFlush(textWebSocketFrame);
        }
//        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(new Date().toString() + ctx.channel().id() + "===>>>" + requestMsg);
//        //发送给客户端，想每一个客户都发
//        NettyConfig.channelGroup.writeAndFlush(textWebSocketFrame);


    }

    /**
     * 处理握手请求的业务
     *
     * @param ctx
     * @param fullHttpRequest
     */
    private void handHttpRequest(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) {
        if (!fullHttpRequest.getDecoderResult().isSuccess() || !("websocket".equals(fullHttpRequest.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, fullHttpRequest, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        HttpMethod method = fullHttpRequest.getMethod();
        String uri = fullHttpRequest.getUri();
        logger.info("uri=" + uri);
        if (method == HttpMethod.GET && "/webssss".equals(uri)) {
            ctx.attr(AttributeKey.valueOf("type")).set("anzhuo");
        } else if (method == HttpMethod.GET && "/websocket".equals(uri)) {
            ctx.attr(AttributeKey.valueOf("type")).set("live");
        }

        WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory(WEBSOCKET_URL, null, false);
        webSocketServerHandshaker = webSocketServerHandshakerFactory.newHandshaker(fullHttpRequest);
        if (webSocketServerHandshaker == null) {
            logger.info("webSocketServerHandshaker is null");
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            logger.info("webSocketServerHandshaker is not null");
            webSocketServerHandshaker.handshake(ctx.channel(), fullHttpRequest);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, DefaultFullHttpResponse response) {
        if (response.getStatus().code() != 200) {
            ByteBuf byteBuf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(byteBuf);
            byteBuf.release();
        }

        ChannelFuture channelFuture = ctx.channel().writeAndFlush(response);
        if (response.getStatus().code() != 200) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 异常调用
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info(ctx.channel().id().asShortText() + "=连接异常：" + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }

    /**
     * 创建连接时调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        boolean add = NettyConfig.channelGroup.add(ctx.channel());
        logger.info("客户端与服务端连接开启：" + add + "=连接的ID=" + ctx.channel().id().asLongText());
    }

    /**
     * 断开连接时调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        boolean remove = NettyConfig.channelGroup.remove(ctx.channel());
        logger.info("客户端与服务端连接关闭:" + remove + "ID=" + ctx.channel().id().asLongText());
    }

    /**
     * 接收数据结束后
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ChannelHandlerContext flush = ctx.flush();
        logger.info("接收数据结束后:" + ctx.channel().id().asShortText());
//        super.channelReadComplete(ctx);
    }
}
