package org.oneandone.tech.poker.leo.messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Cards {
    private List<Card> cards = new ArrayList<>();

    public Cards() {
    }

    public Cards(Collection<Card> cards) {
        this.cards.addAll(cards);
    }

    public List<Card> getCards() {
        return cards;
    }
}
