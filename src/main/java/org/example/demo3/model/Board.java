package org.example.demo3.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Card> cards;

    public Board(List<Card> cards) {
        if (cards.size() > 5) {
            throw new IllegalArgumentException("Board-ul nu poate avea mai mult de 5 cărți");
        }
        this.cards = new ArrayList<>(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
