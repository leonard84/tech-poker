package org.oneandone.tech.poker.leo.messages;

public class ResetRequest {
    private String sessionId;
    private String playerId;

    public ResetRequest() {
    }

    public ResetRequest(String sessionId, String playerId) {
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
