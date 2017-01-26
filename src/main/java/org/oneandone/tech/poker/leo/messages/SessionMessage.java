package org.oneandone.tech.poker.leo.messages;

public class SessionMessage {
    private String sessionId;

    public SessionMessage() {
    }

    public SessionMessage(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
