package org.oneandone.tech.poker.leo.data;

public class PlayerVote {
    private final String playerName;
    private final boolean voted;

    public PlayerVote(String playerName, boolean voted) {
        this.playerName = playerName;
        this.voted = voted;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isVoted() {
        return voted;
    }

    public boolean hasVoted() {
        return voted;
    }
}
