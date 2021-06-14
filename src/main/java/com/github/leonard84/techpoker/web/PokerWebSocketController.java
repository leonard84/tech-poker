package com.github.leonard84.techpoker.web;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.github.leonard84.techpoker.data.Choice;
import com.github.leonard84.techpoker.data.GameId;
import com.github.leonard84.techpoker.data.PlayerId;
import com.github.leonard84.techpoker.exceptions.GameNotFoundException;
import com.github.leonard84.techpoker.messages.Card;
import com.github.leonard84.techpoker.messages.Cards;
import com.github.leonard84.techpoker.messages.JoinRequest;
import com.github.leonard84.techpoker.messages.JoinResponse;
import com.github.leonard84.techpoker.messages.KickMessage;
import com.github.leonard84.techpoker.messages.PlayerStats;
import com.github.leonard84.techpoker.messages.ResetRequest;
import com.github.leonard84.techpoker.messages.SessionMessage;
import com.github.leonard84.techpoker.messages.VoteMessage;
import com.github.leonard84.techpoker.services.GameService;
import com.github.leonard84.techpoker.services.GameSession;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PokerWebSocketController {

    @Inject
    private GameService gameService;

    @Inject
    private MessageSource messageSource;


    @PostMapping(path = "/rest/join", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public JoinResponse join(@RequestBody JoinRequest joinRequest) {
        GameSession gameSession = gameService.searchById(new GameId(joinRequest.getSessionId()))
                .orElseThrow(GameNotFoundException::new);

        return new JoinResponse(gameSession.getId().toString(),
                gameSession.join(joinRequest.getPlayerName()).toString());
    }

    @GetMapping(path = "/rest/cards", produces = "application/json")
    @ResponseBody
    public Cards cards() {
        Locale locale = LocaleContextHolder.getLocaleContext().getLocale();
        List<Card> cards = Arrays.stream(Choice.values())
                .map(choice ->
                        new Card(choice.name(), messageSource.getMessage("card.values." + choice.name(), null, locale)))
                .collect(Collectors.toList());

        return new Cards(cards);
    }

    @GetMapping(path = "/rest/stats/{sessionId}/{playerId}", produces = "application/json")
    @ResponseBody
    public PlayerStats playerStats(@PathVariable("sessionId") String sessionId, @PathVariable("playerId") String playerId) {
        GameSession gameSession = gameService.searchById(new GameId(sessionId)).orElseThrow(GameNotFoundException::new);
        PlayerId player = new PlayerId(playerId);

        return new PlayerStats(gameSession.getName(player),
                Optional.ofNullable(gameSession.getVote(player)).map(Enum::name).orElse(""));
    }


    @MessageMapping("/session/stats")
    public void gameStats(SessionMessage session) {
        Optional<GameSession> gameSession = gameService.searchById(new GameId(session.getSessionId()));
        gameSession.ifPresent(GameSession::sendStats);
    }

    @MessageMapping("/session/reset")
    public void gameReset(SessionMessage session) {
        Optional<GameSession> gameSession = gameService.searchById(new GameId(session.getSessionId()));
        gameSession.ifPresent(GameSession::reset);
    }

    @MessageMapping("/session/request-reset")
    public void gameRequestReset(ResetRequest resetRequest) {
        Optional<GameSession> gameSession = gameService.searchById(new GameId(resetRequest.getSessionId()));
        gameSession.ifPresent(GameSession::requestReset);
    }

    @MessageMapping("/session/tally")
    public void gameTally(SessionMessage session) {
        Optional<GameSession> gameSession = gameService.searchById(new GameId(session.getSessionId()));
        gameSession.ifPresent(GameSession::tally);
    }

    @MessageMapping("/session/vote")
    public void gameVote(VoteMessage voteMessage) {
        Optional<GameSession> gameSession = gameService.searchById(new GameId(voteMessage.getSessionId()));
        gameSession.ifPresent(gs -> gs.vote(new PlayerId(voteMessage.getPlayerId()), Choice.valueOf(voteMessage.getVote())));
    }

    @MessageMapping("/session/kick")
    public void gameKick(KickMessage kickMessage) {
        Optional<GameSession> gameSession = gameService.searchById(new GameId(kickMessage.getSessionId()));
        gameSession.ifPresent(gs -> gs.kickPlayer(new PlayerId(kickMessage.getPlayerId())));
    }
}
