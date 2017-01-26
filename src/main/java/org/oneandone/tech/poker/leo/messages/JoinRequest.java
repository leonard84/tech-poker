package org.oneandone.tech.poker.leo.messages;

public class JoinRequest {
    private String sessionId;
    private String playerName;

    public JoinRequest(String sessionId, String playerName) {
        this.sessionId = sessionId;
        this.playerName = playerName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getPlayerName() {
        return playerName;
    }
}
