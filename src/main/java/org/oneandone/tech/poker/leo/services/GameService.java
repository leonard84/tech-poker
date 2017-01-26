package org.oneandone.tech.poker.leo.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Provider;

import org.oneandone.tech.poker.leo.data.GameId;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Map<GameId, GameSession> games = new ConcurrentHashMap<>();

    public GameSession getById(GameId gameId) {
        return games.get(gameId);
    }

    @Inject
    private Provider<GameSession> gameSessionProvider;

    public GameId createNewGame() {
        GameSession gameSession = gameSessionProvider.get();
        games.put(gameSession.getId(), gameSession);
        return gameSession.getId();
    }
}
