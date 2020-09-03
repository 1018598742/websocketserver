package com.fta.websocketserver.utils;


/**
 * 项目中的全局常量定义
 */
public interface Constants {

    String WEBSOCKET_STR = "websocket";
    String UPGRADE_STR = "Upgrade";
    int OK_CODE = 200;

    String HTTP_CODEC = "http-codec";
    String AGGREGATOR = "aggregator";
    String HTTP_CHUNKED = "http-chunked";
    String HANDLER = "handler";
//    int MAX_CONTENT_LENGTH = 65536;
    int MAX_CONTENT_LENGTH = 10240;
    int PORT = 8989;
//    int PORT = 8982;

    String WEB_SOCKET_URL = "ws://localhost:"+PORT+"/ws";

    //订阅者列表
    String IM_QUEUE_CHANNLID = "im-queue-channlid";
}
