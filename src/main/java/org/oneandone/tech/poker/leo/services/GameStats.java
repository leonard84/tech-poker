package org.oneandone.tech.poker.leo.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameStats {
    private List<String> players = new ArrayList<>();
    private int currentVotes;

    public GameStats(Collection<String> players, int currentVotes) {
        this.players.addAll(players);
        this.currentVotes = currentVotes;
    }

    public List<String> getPlayers() {
        return players;
    }

    public int getCurrentVotes() {
        return currentVotes;
    }
}
