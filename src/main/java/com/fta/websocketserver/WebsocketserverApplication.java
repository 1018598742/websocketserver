package com.fta.websocketserver;

import com.fta.websocketserver.websocket.WebsocketNetty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class WebsocketserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketserverApplication.class, args);
        try {
            new WebsocketNetty().start();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
