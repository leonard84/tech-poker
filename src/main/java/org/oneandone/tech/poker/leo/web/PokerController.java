package org.oneandone.tech.poker.leo.web;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

import org.oneandone.tech.poker.leo.config.PokerProperties;
import org.oneandone.tech.poker.leo.data.Choice;
import org.oneandone.tech.poker.leo.data.GameId;
import org.oneandone.tech.poker.leo.data.PlayerId;
import org.oneandone.tech.poker.leo.exceptions.GameNotFoundException;
import org.oneandone.tech.poker.leo.services.GameService;
import org.oneandone.tech.poker.leo.services.GameSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PokerController {

    private static final List<String> CARDS = Arrays.stream(Choice.values()).map(Enum::name).collect(Collectors.toList());

    @Inject
    private PokerProperties pokerProperties;

    @Inject
    private GameService gameService;

    @GetMapping(path = "/wp")
    public String websocketPlayer() {
        return "websocket-player";
    }


    @GetMapping(path = "/wm/{gameId}")
    public ModelAndView websocketMaster(@PathVariable("gameId") String gameId, ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        modelAndView.addObject("gameId", game.getId());
        modelAndView.addObject("externalUrl", pokerProperties.getExternalUrl());
        modelAndView.setViewName("websocket-master");
        return modelAndView;
    }

    @GetMapping(path = "/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.addObject("sessionCount", gameService.getCurrentSessionCount());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping(path = "/new")
    public View newGame() {
        GameId gameId = gameService.createNewGame();
        return new RedirectView("/game/" + gameId);
    }

    @PostMapping(path = "/new/ws")
    public View newGameWs() {
        GameId gameId = gameService.createNewGame();
        return new RedirectView("/wm/" + gameId);
    }

    @GetMapping(path = "/game/{gameId}")
    public ModelAndView game(@PathVariable("gameId") String gameId, ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        modelAndView.addObject("gameId", game.getId());
        modelAndView.addObject("externalUrl", pokerProperties.getExternalUrl());
        modelAndView.addObject("stats", game.getStats());
        modelAndView.setViewName("poker");
        return modelAndView;
    }

    @PostMapping(path = "/result/{gameId}")
    public ModelAndView result(@PathVariable("gameId") String gameId, ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        modelAndView.addObject("result", game.tally());
        modelAndView.setViewName("result");
        return modelAndView;
    }

    @PostMapping(path = "/reset/{gameId}")
    public View reset(@PathVariable("gameId") String gameId) {
        GameSession game = getGame(gameId);
        game.reset();
        return new RedirectView("/game/" + gameId);
    }

    @GetMapping(path = "/join")
    public ModelAndView requestJoinGame(@RequestParam("gameId") String gameId, ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        modelAndView.addObject("gameId", game.getId());
        modelAndView.setViewName("join");
        return modelAndView;
    }

    @PostMapping(path = "/join/{gameId}")
    public View joinGame(@PathVariable("gameId") String gameId, @FormParam("playerName") @Size(min = 3) String playerName) {
        GameSession game = getGame(gameId);
        PlayerId playerId = game.join(playerName);
        return new RedirectView("/vote/" + gameId + "/" + playerId);
    }


    @PostMapping(path = "/kick/{gameId}")
    public View kickPlayer(@PathVariable("gameId") String gameId, @FormParam("playerId") @Size(min = 36) String playerId) {
        GameSession game = getGame(gameId);
        PlayerId pId = new PlayerId(playerId);
        game.kickPlayer(pId);
        return new RedirectView("/game/" + gameId);
    }

    @PostMapping(path = "/vote/{gameId}/{playerId}")
    public View vote(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId,
            @FormParam("vote") String vote) {
        GameSession game = getGame(gameId);
        PlayerId playerId1 = new PlayerId(playerId);
        game.vote(playerId1, Choice.valueOf(vote.toUpperCase()));
        return new RedirectView("/vote/" + gameId + "/" + playerId);
    }

    @PostMapping(path = "/request-reset/{gameId}/{playerId}")
    public View requestReset(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId,
            @FormParam("vote") String vote) {
        GameSession game = getGame(gameId);
        game.requestReset();
        return new RedirectView("/vote/" + gameId + "/" + playerId);
    }

    @GetMapping(path = "/vote/{gameId}/{playerId}")
    public ModelAndView selection(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId,
            ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        PlayerId playerId1 = new PlayerId(playerId);
        Choice vote = game.getVote(playerId1);
        String playerName = game.getName(playerId1);
        modelAndView.addObject("gameId", game.getId());
        modelAndView.addObject("playerId", playerId1);
        modelAndView.addObject("cards", CARDS);
        modelAndView.addObject("playerName", playerName);
        modelAndView.addObject("vote", Optional.ofNullable(vote).map(Enum::name).orElse(""));
        modelAndView.addObject("externalUrl", pokerProperties.getExternalUrl());
        modelAndView.setViewName("vote");
        return modelAndView;
    }

    private GameSession getGame(String gameId) {
        GameSession gameSession = gameService.getById(new GameId(gameId));
        if (gameSession == null) {
            throw new GameNotFoundException();
        }
        return gameSession;
    }
}
