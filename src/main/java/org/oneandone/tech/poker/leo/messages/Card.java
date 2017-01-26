package org.oneandone.tech.poker.leo.messages;

public class Card {
    private String key;
    private String displayName;

    public Card() {
    }

    public Card(String key, String displayName) {
        this.key = key;
        this.displayName = displayName;
    }

    public String getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }
}
