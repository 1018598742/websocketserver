package com.fta.testwebsocket

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

fun main() {
    val info  = "1234adfa"
    val stringToMD5 = MD5Utils().stringToMD5(info)
    println(stringToMD5)
}

class MD5Utils {


    fun stringToMD5(plainText: String): String? {
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
