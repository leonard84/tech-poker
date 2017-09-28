package org.oneandone.tech.poker.leo;

import org.oneandone.tech.poker.leo.config.PokerProperties;
import org.oneandone.tech.poker.leo.services.GameSession;
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
