package com.fta.websocketserver.bean

data class UpgradeBaseResp<E>(
        var code: String? = null,
        var msg: String? = null,
        var data: E? = null
)