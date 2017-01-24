package org.oneandone.tech.poker.leo.services;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.springframework.util.Assert;

public class GameSession {
    private final GameId id;

    private final Instant creationTime = Instant.now();

    private Instant lastUpdate = Instant.now();

    private Map<PlayerId, Choice> votes = new ConcurrentHashMap<>();

    private Map<PlayerId, String> players = new ConcurrentHashMap<>();

    public GameSession() {
        this.id = new GameId();
    }

    public GameSession(GameId id) {
        Assert.notNull(id, "id may not be null");
        this.id = id;
    }

    public PlayerId join(String playerName) {
        Assert.notNull(playerName, "playerName may not be null");
        updated();
        PlayerId playerId = new PlayerId();
        players.put(playerId, playerName);
        return playerId;
    }

    public void vote(PlayerId playerId, Choice vote) {
        Assert.notNull(playerId, "playerId may not be null");
        Assert.notNull(vote, "vote may not be null");
        updated();
        votes.put(playerId, vote);
    }

    public GameId getId() {
        return id;
    }

    public Result tally() {
        updated();
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
        updated();
        votes.clear();
    }

    public Choice getVote(PlayerId playerId) {
        Assert.notNull(playerId, "playerId may not be null");
        return votes.get(playerId);
    }

    private void updated() {
        lastUpdate = Instant.now();
    }
}
