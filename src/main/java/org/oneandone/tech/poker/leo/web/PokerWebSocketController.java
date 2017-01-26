package org.oneandone.tech.poker.leo.web;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.oneandone.tech.poker.leo.data.Choice;
import org.oneandone.tech.poker.leo.data.GameId;
import org.oneandone.tech.poker.leo.data.PlayerId;
import org.oneandone.tech.poker.leo.messages.Card;
import org.oneandone.tech.poker.leo.messages.Cards;
import org.oneandone.tech.poker.leo.messages.JoinRequest;
import org.oneandone.tech.poker.leo.messages.JoinResponse;
import org.oneandone.tech.poker.leo.messages.SessionMessage;
import org.oneandone.tech.poker.leo.messages.VoteMessage;
import org.oneandone.tech.poker.leo.services.GameService;
import org.oneandone.tech.poker.leo.services.GameSession;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PokerWebSocketController {

    @Inject
    private GameService gameService;

    @Inject
    private MessageSource messageSource;


    @RequestMapping(path = "/rest/join", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public JoinResponse join(@RequestBody JoinRequest joinRequest) {
        GameSession gameSession = gameService.getById(new GameId(joinRequest.getSessionId()));
        return new JoinResponse(gameSession.getId().toString(),
                gameSession.join(joinRequest.getPlayerName()).toString());
    }

    @RequestMapping(path = "/rest/cards", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Cards cards() {
        Locale locale = LocaleContextHolder.getLocaleContext().getLocale();
        List<Card> cards = Arrays.stream(Choice.values())
                .map(choice ->
                    new Card(choice.name(), messageSource.getMessage("card.values." + choice.name(), null, locale)))
                .collect(Collectors.toList());

        return new Cards(cards);
    }


    @MessageMapping("/session/stats")
    public void gameStats(SessionMessage session) {
        GameSession gameSession = gameService.getById(new GameId(session.getSessionId()));
        gameSession.sendStats();
    }

    @MessageMapping("/session/reset")
    public void gameReset(SessionMessage session) {
        GameSession gameSession = gameService.getById(new GameId(session.getSessionId()));
        gameSession.reset();
    }

    @MessageMapping("/session/tally")
    public void gameTally(SessionMessage session) {
        GameSession gameSession = gameService.getById(new GameId(session.getSessionId()));
        gameSession.tally();
    }

    @MessageMapping("/session/vote")
    public void gameVote(VoteMessage voteMessage) {
        GameSession gameSession = gameService.getById(new GameId(voteMessage.getSessionId()));
        gameSession.vote(new PlayerId(voteMessage.getPlayerId()), Choice.valueOf(voteMessage.getVote()));
    }
}
