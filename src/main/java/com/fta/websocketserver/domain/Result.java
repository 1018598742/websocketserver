package com.fta.websocketserver.domain;

import lombok.Data;

@Data
public class Result<T> {


    private Integer status;

    private String msg;

    private T content;
}
