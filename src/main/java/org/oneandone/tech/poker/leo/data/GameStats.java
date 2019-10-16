package org.oneandone.tech.poker.leo.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameStats {
    private List<PlayerVote> playerVotes = new ArrayList<>();
    private int currentVotes;
    private boolean resetRequested;

    public GameStats(Collection<PlayerVote> playerVotes, int currentVotes, boolean resetRequested) {
        this.playerVotes.addAll(playerVotes);
        this.currentVotes = currentVotes;
        this.resetRequested = resetRequested;
    }

    public List<PlayerVote> getPlayerVotes() {
        return playerVotes;
    }

    public int getCurrentVotes() {
        return currentVotes;
    }

    public boolean isresetRequested() {
        return resetRequested;
    }
}
