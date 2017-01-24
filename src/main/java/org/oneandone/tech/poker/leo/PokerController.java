package org.oneandone.tech.poker.leo;

import javax.inject.Inject;
import javax.ws.rs.FormParam;

import org.oneandone.tech.poker.leo.config.PokerConfig;
import org.oneandone.tech.poker.leo.exceptions.GameNotFoundException;
import org.oneandone.tech.poker.leo.services.GameId;
import org.oneandone.tech.poker.leo.services.GameService;
import org.oneandone.tech.poker.leo.services.GameSession;
import org.oneandone.tech.poker.leo.services.PlayerId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PokerController {

    @Inject
    private PokerConfig pokerConfig;

    @Inject
    private GameService gameService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public View newGame() {
        GameId gameId = gameService.createNewGame();
        return new RedirectView("/game/"+gameId);
    }

    @RequestMapping(path = "/game/{gameId}", method = RequestMethod.GET)
    public ModelAndView game(@PathVariable("gameId") String gameId, ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        modelAndView.addObject("gameId", game.getId());
        modelAndView.addObject("externalUrl", pokerConfig.getExternalUrl());
        modelAndView.setViewName("poker");
        return modelAndView;
    }

    @RequestMapping(path = "/join", method = RequestMethod.GET)
    public ModelAndView requestJoinGame(@RequestParam("gameId") String gameId,ModelAndView modelAndView) {
        GameSession game = getGame(gameId);
        modelAndView.addObject("gameId", game.getId());
        modelAndView.setViewName("join");
        return modelAndView;
    }

    @RequestMapping(path = "/join/{gameId}", method = RequestMethod.POST)
    public View joinGame(@PathVariable("gameId") String gameId, @FormParam("playerName") String playerName) {
        GameSession game = getGame(gameId);
        PlayerId playerId = game.join(playerName);
        return new RedirectView("/vote/"+gameId+"/"+playerId);
    }

    private GameSession getGame(String gameId) {
        GameSession gameSession = gameService.getById(new GameId(gameId));
        if (gameSession == null) {
            throw new GameNotFoundException();
        }
        return gameSession;
    }
}
