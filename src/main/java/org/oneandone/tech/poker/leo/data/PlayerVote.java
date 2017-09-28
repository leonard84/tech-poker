package org.oneandone.tech.poker.leo.data;

public class PlayerVote {
    private final PlayerId playerId;
    private final String playerName;
    private final boolean voted;

    public PlayerVote(PlayerId playerId, String playerName, boolean voted) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.voted = voted;
    }

    public PlayerId getPlayerId() {
        return playerId;
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
