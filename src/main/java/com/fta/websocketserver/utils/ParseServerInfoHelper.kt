package com.fta.websocketserver.utils

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object ParseServerInfoHelper {

    fun generByteString(flage: Int, content: String): ByteBuf? {
//        val flageByteArray = int2ByteArray(flage)
        val flageByteArray = TranUtils.short2byte(flage.toShort())
        val contentByteArray = content.toByteArray()
        val contentLength = contentByteArray.size
        val contentLengthByteArray = int2ByteArray(contentLength)
        val vertifyInfo = stringToMD5(content)
        if (vertifyInfo != null) {
            val vertifyByteArray =  vertifyInfo.toByteArray()
            val flageLen = flageByteArray.size
            val contenLenLen = contentLengthByteArray.size
            val vertifyLen = vertifyByteArray.size
            val totalLen = flageLen + contenLenLen + vertifyLen + contentLength

            val newByteArray = ByteArray(totalLen)

            //添加标记数组
            System.arraycopy(flageByteArray, 0, newByteArray, 0, flageLen)
            //添加长度数组
            System.arraycopy(contentLengthByteArray, 0, newByteArray, flageLen, contenLenLen)
            //添加验证信息数组
            System.arraycopy(vertifyByteArray, 0, newByteArray, flageLen + contenLenLen, vertifyLen)
            //添加内容数组
            System.arraycopy(
                contentByteArray,
                0,
                newByteArray,
                totalLen - contentLength,
                contentLength
            )

            val byteBuf = Unpooled.directBuffer(newByteArray.size)
            byteBuf.writeBytes(newByteArray)
            return byteBuf
        }
        return null
    }

    fun generByteString(flage: Short, content: String): ByteBuf? {
//        val flageByteArray = int2ByteArray(flage)
        val flageByteArray = TranUtils.short2byte(flage)
        val contentByteArray = content.toByteArray()
        val contentLength = contentByteArray.size
        val contentLengthByteArray = int2ByteArray(contentLength)
        val vertifyInfo = stringToMD5(content)
        if (vertifyInfo != null) {
            val vertifyByteArray =  vertifyInfo.toByteArray()
            val flageLen = flageByteArray.size
            val contenLenLen = contentLengthByteArray.size
            val vertifyLen = vertifyByteArray.size
            val totalLen = flageLen + contenLenLen + vertifyLen + contentLength

            val newByteArray = ByteArray(totalLen)

            //添加标记数组
            System.arraycopy(flageByteArray, 0, newByteArray, 0, flageLen)
            //添加长度数组
            System.arraycopy(contentLengthByteArray, 0, newByteArray, flageLen, contenLenLen)
            //添加验证信息数组
            System.arraycopy(vertifyByteArray, 0, newByteArray, flageLen + contenLenLen, vertifyLen)
            //添加内容数组
            System.arraycopy(
                    contentByteArray,
                    0,
                    newByteArray,
                    totalLen - contentLength,
                    contentLength
            )

            val byteBuf = Unpooled.directBuffer(newByteArray.size)
            byteBuf.writeBytes(newByteArray)
            return byteBuf
        }
        return null
    }


    private fun int2ByteArray(i: Int): ByteArray {
        val result = ByteArray(4)
        // 由高位到低位
        result[0] = (i shr 24 and 0xFF).toByte()
        result[1] = (i shr 16 and 0xFF).toByte()
        result[2] = (i shr 8 and 0xFF).toByte()
        result[3] = (i and 0xFF).toByte()
        return result
    }

    private fun byteArray2Int(bytes: ByteArray): Int {
        var value = 0
        // 由高位到低位
        for (i in 0..3) {
            val shift = (4 - 1 - i) * 8
            value += bytes.get(i).toInt() and 0x000000FF shl shift // 往高位游
        }
        return value
    }

    private fun stringToMD5(plainText: String): String? {
        var secretBytes: ByteArray? = null
        secretBytes = try {
            MessageDigest.getInstance("md5").digest(
                plainText.toByteArray()
            )

        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("没有这个md5算法！")
        }
        var md5code: String = BigInteger(1, secretBytes).toString(16)
        for (i in 0 until 32 - md5code.length) {
            md5code = "0$md5code"
        }
        return md5code
    }
}