package com.fta.websocketserver.controller;

import com.fta.websocketserver.domain.Person;
import com.fta.websocketserver.domain.PersonUpgrade;
import com.fta.websocketserver.domain.Result;
import com.fta.websocketserver.utils.ResultUtil;
import com.fta.websocketserver.websocket.NettyConfig;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("")
public class HelloController {

    private final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/say")
    public String say() {
        return "Hello SpringBoot!";
    }


    @RequestMapping("/user/data")
    public Result getByPerson(@Valid Person person, String userId) {
        // 直接将json信息打印出来
        logger.info(person.toString() + ";userId={}", userId);
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame("hello websocket");
//        NettyConfig.channelGroup.writeAndFlush(textWebSocketFrame);
        PersonUpgrade personUpgrade = new PersonUpgrade();
        personUpgrade.setName(person.getName());
        personUpgrade.setAge(person.getAge());
        personUpgrade.setTime(System.currentTimeMillis());
        return ResultUtil.success(personUpgrade);
    }

//    @RequestMapping("/user/data")
//    public Result getByPerson(String userId,String name, int age) {
//        // 直接将json信息打印出来
//        logger.info("userId={};name={};age={}",userId ,name, age);
//        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame("hello websocket");
//        NettyConfig.channelGroup.writeAndFlush(textWebSocketFrame);
//        Person person = new Person();
//        person.setAge(age);
//        person.setName(name);
//        return ResultUtil.success(person);
//    }

}
