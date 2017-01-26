package org.oneandone.tech.poker.leo.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameStats {
    private List<PlayerVote> playerVotes = new ArrayList<>();
    private int currentVotes;

    public GameStats(Collection<PlayerVote> playerVotes, int currentVotes) {
        this.playerVotes.addAll(playerVotes);
        this.currentVotes = currentVotes;
    }

    public List<PlayerVote> getPlayerVotes() {
        return playerVotes;
    }

    public int getCurrentVotes() {
        return currentVotes;
    }
}
