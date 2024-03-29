package com.github.leonard84.techpoker.web;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.github.leonard84.techpoker.config.PokerProperties;
import com.github.leonard84.techpoker.data.Choice;
import com.github.leonard84.techpoker.data.GameId;
import com.github.leonard84.techpoker.data.PlayerId;
import com.github.leonard84.techpoker.exceptions.GameNotFoundException;
import com.github.leonard84.techpoker.services.GameService;
import com.github.leonard84.techpoker.services.GameSession;

@Controller
public class PokerController {

    private static final List<String> CARDS = Arrays.stream(Choice.values()).map(Enum::name).toList();

    public static final String VIEW_VOTE = "/vote/";

    public static final String VIEW_GAME = "/game/";

    public static final String EXTERNAL_URL = "externalUrl";

    public static final String GAME_ID = "gameId";

    @Autowired
    private PokerProperties pokerProperties;

    @Autowired
    private GameService gameService;

    @GetMapping(path = "/wp")
    public String websocketPlayer() {
        return "websocket-player";
    }

    @GetMapping(path = "/wm/{gameId}")
    public ModelAndView websocketMaster(@PathVariable(GAME_ID) String gameId, ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        modelAndView.addObject(GAME_ID, game.getId());
        modelAndView.addObject(EXTERNAL_URL, pokerProperties.getExternalUrl());
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
        return new RedirectView(VIEW_GAME + gameId);
    }

    @PostMapping(path = "/new/ws")
    public View newGameWs() {
        GameId gameId = gameService.createNewGame();
        return new RedirectView("/wm/" + gameId);
    }

    @GetMapping(path = "/game/{gameId}")
    public ModelAndView game(@PathVariable(GAME_ID) String gameId, ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        modelAndView.addObject(GAME_ID, game.getId());
        modelAndView.addObject(EXTERNAL_URL, pokerProperties.getExternalUrl());
        modelAndView.addObject("stats", game.getStats());
        modelAndView.setViewName("poker");
        return modelAndView;
    }

    @PostMapping(path = "/result/{gameId}")
    public ModelAndView result(@PathVariable(GAME_ID) String gameId, ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        modelAndView.addObject("result", game.tally());
        modelAndView.setViewName("result");
        return modelAndView;
    }

    @PostMapping(path = "/reset/{gameId}")
    public View reset(@PathVariable(GAME_ID) String gameId) {
        GameSession game = getGame(gameId);
        game.reset();
        return new RedirectView(VIEW_GAME + gameId);
    }

    @GetMapping(path = "/join")
    public ModelAndView requestJoinGame(@RequestParam(GAME_ID) String gameId, ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        modelAndView.addObject(GAME_ID, game.getId());
        modelAndView.addObject(EXTERNAL_URL, pokerProperties.getExternalUrl());
        modelAndView.setViewName("join");
        return modelAndView;
    }

    @PostMapping(path = "/join/{gameId}")
    public View joinGame(@PathVariable(GAME_ID) String gameId,
            @RequestParam("playerName") @Size(min = 3) String playerName) {
        GameSession game = getGame(gameId);
        PlayerId playerId = game.join(playerName);
        return new RedirectView(VIEW_VOTE + gameId + "/" + playerId);
    }

    @PostMapping(path = "/kick/{gameId}")
    public View kickPlayer(@PathVariable(GAME_ID) String gameId,
            @RequestParam("playerId") @Size(min = 36) String playerId) {
        GameSession game = getGame(gameId);
        PlayerId pId = new PlayerId(playerId);
        game.kickPlayer(pId);
        return new RedirectView(VIEW_GAME + gameId);
    }

    @PostMapping(path = "/vote/{gameId}/{playerId}")
    public View vote(@PathVariable(GAME_ID) String gameId, @PathVariable("playerId") String playerId,
            @RequestParam("vote") String vote) {
        GameSession game = getGame(gameId);
        PlayerId playerId1 = new PlayerId(playerId);
        game.vote(playerId1, Choice.valueOf(vote.toUpperCase()));
        return new RedirectView(VIEW_VOTE + gameId + "/" + playerId);
    }

    @PostMapping(path = "/request-reset/{gameId}/{playerId}")
    public View requestReset(@PathVariable(GAME_ID) String gameId, @PathVariable("playerId") String playerId) {
        GameSession game = getGame(gameId);
        game.requestReset();
        return new RedirectView(VIEW_VOTE + gameId + "/" + playerId);
    }

    @GetMapping(path = "/vote/{gameId}/{playerId}")
    public ModelAndView selection(@PathVariable(GAME_ID) String gameId, @PathVariable("playerId") String playerId,
            ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        PlayerId playerId1 = new PlayerId(playerId);
        Choice vote = game.getVote(playerId1);
        String playerName = game.getName(playerId1);
        modelAndView.addObject(GAME_ID, game.getId());
        modelAndView.addObject("playerId", playerId1);
        modelAndView.addObject("cards", CARDS);
        modelAndView.addObject("playerName", playerName);
        modelAndView.addObject("vote", Optional.ofNullable(vote).map(Enum::name).orElse(""));
        modelAndView.addObject(EXTERNAL_URL, pokerProperties.getExternalUrl());
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
