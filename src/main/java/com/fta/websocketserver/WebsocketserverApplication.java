package com.fta.websocketserver;

import com.fta.websocketserver.websocket.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsocketserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketserverApplication.class, args);
        new Main().start();
    }
}
