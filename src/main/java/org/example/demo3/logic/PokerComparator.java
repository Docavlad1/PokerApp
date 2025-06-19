package org.example.demo3.logic;

import org.example.demo3.model.Card;
import org.example.demo3.model.Hand;
import org.example.demo3.model.Board;

import java.util.ArrayList;
import java.util.List;

public class PokerComparator {

    public static int compare(Hand h1, Hand h2, Board board) {
        List<Card> full1 = new ArrayList<>(h1.getCards());
        full1.addAll(board.getCards());

        List<Card> full2 = new ArrayList<>(h2.getCards());
        full2.addAll(board.getCards());

        PokerHandEvaluator.EvaluatedHand eval1 = PokerHandEvaluator.evaluate(full1);
        PokerHandEvaluator.EvaluatedHand eval2 = PokerHandEvaluator.evaluate(full2);

        return eval1.compareTo(eval2); // <0 dacă h2 câștigă
    }
}
