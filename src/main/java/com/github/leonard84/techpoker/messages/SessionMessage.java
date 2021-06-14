package com.github.leonard84.techpoker.messages;

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
