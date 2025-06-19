package org.example.demo3.model;

import java.util.List;

public class Hand {
    private final Card card1;
    private final Card card2;

    public Hand(Card card1, Card card2) {
        if (card1.equals(card2)) {
            throw new IllegalArgumentException("Mâna nu poate avea două cărți identice");
        }
        this.card1 = card1;
        this.card2 = card2;
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }

    public List<Card> getCards() {
        return List.of(card1, card2);
    }

    @Override
    public String toString() {
        return card1 + " | " + card2;
    }
}
