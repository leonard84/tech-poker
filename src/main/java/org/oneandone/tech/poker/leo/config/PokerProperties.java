package org.oneandone.tech.poker.leo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "poker")
public class PokerProperties {

    private String externalUrl;

    private long cleanupInterval = 60_000;

    private long cleanupExpired = 6_000_000;

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public long getCleanupInterval() {
        return cleanupInterval;
    }

    public void setCleanupInterval(long cleanupInterval) {
        this.cleanupInterval = cleanupInterval;
    }

    public long getCleanupExpired() {
        return cleanupExpired;
    }

    public void setCleanupExpired(long cleanupExpired) {
        this.cleanupExpired = cleanupExpired;
    }
}
