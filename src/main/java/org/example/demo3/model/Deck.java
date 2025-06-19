package org.example.demo3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public void remove(Card card) {
        cards.remove(card);
    }

    public void removeAll(List<Card> cardsToRemove) {
        cards.removeAll(cardsToRemove);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}
