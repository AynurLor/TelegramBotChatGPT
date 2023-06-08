package ru.school21.assistancegpt.telegtambot;

import jakarta.ws.rs.core.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class AssistanceGptTelegtamBotApplication {

    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(AssistanceGptTelegtamBotApplication.class);
//        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
//        app.run(args);

        SpringApplication.run(AssistanceGptTelegtamBotApplication.class, args);
    }

}
