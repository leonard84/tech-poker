package org.oneandone.tech.poker.leo.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.springframework.util.Assert;

public class GameSession {
    private final GameId id;

    private Map<PlayerId, Choice> votes = new ConcurrentHashMap<>();

    private Map<PlayerId, String> players = new ConcurrentHashMap<>();

    public GameSession(GameId id) {
        Assert.notNull(id, "id may not be null");
        this.id = id;
    }

    public PlayerId join(String playerName) {
        Assert.notNull(id, "playerName may not be null");
        PlayerId playerId = new PlayerId();
        players.put(playerId, playerName);
        return playerId;
    }

    public void vote(PlayerId playerId, Choice vote) {
        Assert.notNull(playerId, "playerId may not be null");
        Assert.notNull(vote, "vote may not be null");
        votes.put(playerId, vote);
    }

    public GameId getId() {
        return id;
    }

    public Result tally() {
        return new Result(
            resultStream().average().orElse(0.0),
            resultStream().min().orElse(0),
            resultStream().max().orElse(0));
    }

    private IntStream resultStream() {
        return votes.values().stream().filter(Choice::hasValue).mapToInt(Choice::getValue);
    }

    public GameStats getStats() {
        return new GameStats(players.values(), votes.size());
    }

    public void reset() {
        votes.clear();
    }
}
