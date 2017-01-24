package org.oneandone.tech.poker.leo;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.FormParam;

import org.oneandone.tech.poker.leo.config.PokerConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PokerController {
    @Inject
    private PokerConfig pokerConfig;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public View newGame() {
        String gameId = UUID.randomUUID().toString();
        return new RedirectView("/game/"+gameId);
    }

    @RequestMapping(path = "/game/{gameId}", method = RequestMethod.GET)
    public ModelAndView game(@PathVariable("gameId") String gameId, ModelAndView modelAndView) {
        modelAndView.addObject("gameId", gameId);
        modelAndView.addObject("externalUrl", pokerConfig.getExternalUrl());
        modelAndView.setViewName("poker");
        return modelAndView;
    }

    @RequestMapping(path = "/join/{gameId}", method = RequestMethod.GET)
    public ModelAndView requestJoinGame(@PathVariable("gameId") String gameId,ModelAndView modelAndView) {
        modelAndView.addObject("gameId", gameId);
        modelAndView.setViewName("join");
        return modelAndView;
    }

    @RequestMapping(path = "/join/{gameId}", method = RequestMethod.POST)
    public View joinGame(@PathVariable("gameId") String gameId, @FormParam("playerName") String playerName) {
        String playerId = UUID.randomUUID().toString();
        return new RedirectView("/vote/"+gameId+"/"+playerId);
    }
}
