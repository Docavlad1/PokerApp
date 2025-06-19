package org.example.demo3.logic;

import org.example.demo3.model.Card;
import org.example.demo3.model.Deck;
import org.example.demo3.model.Hand;

import java.util.ArrayList;
import java.util.List;

public class OpponentHandGenerator {

    public static List<Hand> generateAllPossibleHands(List<Card> excludedCards) {
        Deck deck = new Deck();
        deck.removeAll(excludedCards);

        List<Card> remaining = deck.getCards();
        List<Hand> possibleHands = new ArrayList<>();

        for (int i = 0; i < remaining.size(); i++) {
            for (int j = i + 1; j < remaining.size(); j++) {
                possibleHands.add(new Hand(remaining.get(i), remaining.get(j)));
            }
        }

        return possibleHands;
    }
}
