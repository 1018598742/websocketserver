package com.fta.websocketserver.controller;

import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/index-websocket")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("socket");
        String uid = RandomUtil.randomNumbers(6);
        logger.info("random info =" + uid);
        modelAndView.addObject("websocket", new WebSocketBean(uid));
        return modelAndView;
    }

    @Data
    @AllArgsConstructor
    public class WebSocketBean {
        private String uid;
    }
}
