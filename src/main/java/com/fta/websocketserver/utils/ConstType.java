package com.fta.websocketserver.utils;


public class ConstType {
    //完成下发或删除指定应用包名指令后的回调接口(应用白名单;应用黑名单)
    public static final int APPLISTCB = 0x384;
    //防卸载回调
    public static final int PREVEUNINSTALLCB = 0x385;
    //完成下发指定应用包名指令后的回调接口(安装应用源白名单)
    public static final int INSTALLSOURCECB = 0x386;
    //获取应用安装白名单
    public static final int INSTALLWHITE = 0x387;
    //清除策略回调
    public static final int RMOVESTRACB = 0x388;
    //修改密码
    public static final int UPDATEPWD = 0x389;
    //华为Push回调接口，回调成功后，执行华为推送的指令(老格式统一回调的接口)
    public static final int HWPUSHCB = 0x38A;
    //完成App安装(下发应用安装，安装成功和下载完轻应用后回调)
    public static final int APPINSTALLCB = 0x38B;
    //完成卸载App(下发卸载应用回调)
    public static final int UNINSTALLCB = 0x38C;
    //文档下发下载完成回调
    public static final int DOCDOWNLOADCB = 0x38D;
    //上传app使用信息
    public static final int UPLOADAPPINFO = 0x38E;
    //策略回调（收到围栏策略后）
    public static final int STRACB = 0x38F;
    //Apn 回调（设置apn后）
    public static final int APNCB = 0x390;
    //vpn 回调(设置vpn后)
    public static final int VPNCB = 0x391;
    //加载所有信息
    public static final int LOADALL = 0x392;
    //加载卸载的App的信息（防卸载白名单移除之后，拉取需要卸载的应用）
    public static final int LOADPREUNINSTALLAPP = 0x393;
    //加载静默安装的应用（安装白名单移除后，拉取需要安装apk）
    public static final int LOADSILENTAPP = 0x394;
    //上传推送令牌
    public static final int UPLOADTOKEN = 0x395;
    //下发白名单回调(修改网络白名单)
    public static final int NETWHITECB = 0x396;
    //下发水印回调
    public static final int WATERMARKCB = 0x397;
    //收到服务器更新指令后回调的接口(更换 ip 回调)
    public static final int CHANGIPCB = 0x398;
    //敏感词回调(收到策略)
    public static final int SENWORDCB = 0x399;
    //由于敏感词违规的设备
    public static final int SENILLEGALCB = 0x39A;
    //离线锁屏解锁成功回调
    public static final int UNLOCKCB = 0x39B;
    //设备锁定时回调(锁定activity)
    public static final int LOCKDEVICECB = 0x39C;
    //设备解绑回调
    public static final int UNBINDCB = 0x39D;
    //锁屏规则密码时回调
    public static final int LOCKRULECB = 0x39E;
    //接口指令回调（新格式回调）
    public static final int NEWORDERCB = 0x39F;
    //接收到流量控制策略后回调
    public static final int TRAFFICCB = 0x3A0;
    //内容策略触发时调用
    public static final int CONTENTSTRA = 0x3A1;
    //接收到内容策略回调
    public static final int RECCONTENTSTRACB = 0x3A2;
    //wifi配置回调
    public static final int WIFISETTINGCB = 0x3A3;
    //蓝牙白名单回调
    public static final int BLEWHITECB = 0x3A4;
    //合规策略新增或者更新回调
    public static final int COMPLIANCECB = 0x3A5;
    //合规策略是否违规
    public static final int COMILLCB = 0x3A6;
    //执行淘汰设备擦除数据或者恢复出厂设置之前调用
    public static final int WEEDOUTCB = 0x3A7;
    //点名回调
    public static final int ROLLCB = 0x3A8;
    //上传拍照接口
    public static final int UPLOADCAM = 0x3A9;
    //应用安装信息(有应用安装就上传应用信息)
    public static final int APPINSTALLINFO = 0x3AA;
    //卸载回调(有应用卸载就上传应用信息)
    public static final int UNINSTALLAPPINFO = 0x3AB;
    //设备通话记录
    public static final int UPLOADRECORD = 0x3AC;
    //设备短信记录
    public static final int UPLOADSMS = 0x3AD;
    //设备流量统计(每个应用的流量使用情况)
    public static final int UPLOADTRAFFIC = 0x3AE;
    //收到删除用户指令的回调
    public static final int DELUSERCB = 0x3AF;
    //上传定位点(轨迹)
    public static final int UPLOADLOCINFOS = 0x3B0;
    //更新定位点(位置变化调)
    public static final int UPDATELOC = 0x3B1;
    //密码规则策略回调(收到密码规则策略后调)
    public static final int PWDRULECB = 0x3B2;
    //应用使用排行
    public static final int APPUSEINFO = 0x3B3;
    //上传设备信息接口
    public static final int DEVINFO = 0x3B4;
    //上传日志(2:网页浏览；3:开机；4:关机；5:卸载应用；6:应用安装；7:打开应用)
    public static final int UPLOADLOG = 0x3B5;
    //签到
    public static final int SIGNIN = 0x3B6;
    //应用权限回调(收到回调)
    public static final int APPPERCB = 0x3B7;
    //应用升级回调
    public static final int APPUPDATECB = 0x3B8;
    //清除管控指令回调
    public static final int CLEARORDERCB = 0x3B9;
    //更新围栏策略状态调用
    public static final int UPDATEFENCESTATUS = 0x3BA;
    //上传截屏接口
    public static final int UPLOADSCREENSHOT = 0x3BB;
    //显示所有应用
    public static final int SHOWALLAPK = 0x3BC;
    //显示所有应用
    public static final int SHOWALLAPKCLI = 0x3BD;
    //上传卸载码
    public static final int UPUNINSTALLCODE = 0x3BE;
    //下发
    public static final int ISSUEDORDER = 0x3BF;
    //服务端主动发送心跳
    public static final int SERVERHEART = 0x3C0;
    //客户端主动发送心跳
    public static final int CLIENTHEART = 0x3C1;
}
