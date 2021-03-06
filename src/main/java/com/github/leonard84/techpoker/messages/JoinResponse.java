package com.github.leonard84.techpoker.messages;

public class JoinResponse {
    private String sessionId;
    private String playerId;

    public JoinResponse() {
    }

    public JoinResponse(String sessionId, String playerId) {
        this.sessionId = sessionId;
        this.playerId = playerId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
