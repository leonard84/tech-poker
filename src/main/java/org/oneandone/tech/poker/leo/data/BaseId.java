package org.oneandone.tech.poker.leo.data;

import java.util.Objects;
import java.util.UUID;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonValue;

public abstract class BaseId {
    private final String id;

    public BaseId(String id) {
        Assert.notNull(id, "Id must not be null");
        this.id = id;
    }

    public BaseId() {
        id = UUID.randomUUID().toString();
    }

    @JsonValue
    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return Objects.equals(this.getClass(),obj.getClass()) && Objects.equals(id, ((BaseId)obj).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
