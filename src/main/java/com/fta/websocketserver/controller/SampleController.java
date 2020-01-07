package com.fta.websocketserver.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class SampleController {
    @GetMapping("/index")
    public ModelAndView index(Model model){
        ModelAndView modelAndView = new ModelAndView("index");

        List<Fans> fans = new ArrayList<>();
        fans.add(new Fans(1L, "zhangsan"));
        fans.add(new Fans(2L, "lis"));
        fans.add(new Fans(3L, "wangwu"));

        modelAndView.addObject("user", new User(1L, "mengday123", "xxx@gmail.com", fans, true, new Date()));

        return modelAndView;
    }


    @Data
    @AllArgsConstructor
    public class User {
        private Long id;
        private String username;
        private String email;
        private List<Fans> fans;
        private Boolean isAdmin;
        private Date birthday;
    }

    @Data
    @AllArgsConstructor
    public class Fans {
        private Long id;
        private String name;
    }
}
