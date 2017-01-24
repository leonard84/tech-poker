package org.oneandone.tech.poker.leo.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Map<GameId, GameSession> games = new ConcurrentHashMap<>();

    public GameSession getById(GameId gameId) {
        return games.get(gameId);
    }

    public GameId createNewGame() {
        GameSession gameSession = new GameSession();
        games.put(gameSession.getId(), gameSession);
        return gameSession.getId();
    }
}
