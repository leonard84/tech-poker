package org.oneandone.tech.poker.leo.messages;

public class VoteMessage {
    private String sessionId;
    private String playerId;
    private String vote;

    public VoteMessage() {
    }

    public VoteMessage(String sessionId, String playerId, String vote) {
        this.sessionId = sessionId;
        this.playerId = playerId;
        this.vote = vote;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getVote() {
        return vote;
    }
}
