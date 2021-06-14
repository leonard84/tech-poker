package com.github.leonard84.techpoker.messages;

public class PlayerStats {
    private String playerName;
    private String vote;

    public PlayerStats() {
    }

    public PlayerStats(String playerName, String vote) {
        this.playerName = playerName;
        this.vote = vote;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getVote() {
        return vote;
    }
}
