package com.fta.websocketserver.utils

import java.net.InetAddress
import java.net.UnknownHostException


fun main() {
    val parseHostGetIPAddress = parseHostGetIPAddress("mdm-ws.geely.com")
    parseHostGetIPAddress?.forEach {
        println(it)
    }
}


/**
 * 解析域名获取IP数组
 *
 * @param host
 * @return
 */
 fun parseHostGetIPAddress(host: String): Array<String?>? {
    var ipAddressArr: Array<String?>? = null
    try {
        val inetAddressArr = InetAddress.getAllByName(host)
        if (inetAddressArr != null && inetAddressArr.size > 0) {
            ipAddressArr = arrayOfNulls(inetAddressArr.size)
            for (i in inetAddressArr.indices) {
                ipAddressArr[i] = inetAddressArr[i].hostAddress
            }
        }
    } catch (e: UnknownHostException) {
        e.printStackTrace()
        return null
    }
    return ipAddressArr
}