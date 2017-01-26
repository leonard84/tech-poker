package org.oneandone.tech.poker.leo.web;

import javax.inject.Inject;

import org.oneandone.tech.poker.leo.data.GameId;
import org.oneandone.tech.poker.leo.messages.JoinRequest;
import org.oneandone.tech.poker.leo.services.GameService;
import org.oneandone.tech.poker.leo.services.GameSession;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PokerWebSocketController {

    @Inject
    private GameService gameService;

    @MessageMapping("/join")
    @SendTo("/topic/requestJoin")
    public String greeting(String name) throws Exception {
        Thread.sleep(1000); // simulated delay
//        GameSession gameSession = gameService.getById(new GameId(request.getSessionId()));
        return "hello " + name;
    }

}
