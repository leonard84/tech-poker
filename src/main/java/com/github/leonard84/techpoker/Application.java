package com.github.leonard84.techpoker;

import com.github.leonard84.techpoker.config.PokerProperties;
import com.github.leonard84.techpoker.services.GameSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@EnableConfigurationProperties(PokerProperties.class)
@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean @Scope("prototype")
    public GameSession gameSession() {return new GameSession();}

}
