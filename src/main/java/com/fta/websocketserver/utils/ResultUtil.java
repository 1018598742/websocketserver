package com.fta.websocketserver.utils;


import com.fta.websocketserver.domain.Result;

public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        result.setStatus(200);
        result.setMsg("执行成功");
        result.setContent(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();

        result.setStatus(code);
        result.setMsg(msg);
        return result;
    }
}
