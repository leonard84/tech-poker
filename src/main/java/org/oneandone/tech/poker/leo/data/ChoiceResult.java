package org.oneandone.tech.poker.leo.data;

import java.util.ArrayList;
import java.util.Collection;

public class ChoiceResult {
    private Choice choice;
    private int count;
    private Collection<String> players = new ArrayList<>();

    public ChoiceResult(Choice choice, int count, Collection<String> players) {
        this.choice = choice;
        this.count = count;
        this.players.addAll(players);
    }

    public Choice getChoice() {
        return choice;
    }

    public int getCount() {
        return count;
    }

    public Collection<String> getPlayers() {
        return players;
    }
}
