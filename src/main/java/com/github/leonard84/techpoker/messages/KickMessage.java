package com.github.leonard84.techpoker.messages;

public class KickMessage {
    private String sessionId;
    private String playerId;

    public KickMessage() {
    }

    public KickMessage(String sessionId, String playerId) {
        this.sessionId = sessionId;
        this.playerId = playerId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
