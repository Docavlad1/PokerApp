
package org.example.demo3.logic;

import org.example.demo3.model.Board;
import org.example.demo3.model.Card;
import org.example.demo3.model.GameState;
import org.example.demo3.model.Hand;
import org.example.demo3.logic.PokerHandEvaluator.EvaluatedHand;
import org.example.demo3.logic.PokerHandType;

import java.util.*;

public class DefeatAnalyzer {

    public static Map<PokerHandType, List<Hand>> getHandsThatBeatPlayerByType(GameState gameState) {
        Hand playerHand = gameState.getPlayerHand();
        Board board = gameState.getBoard();

        Map<PokerHandType, List<Hand>> map = new HashMap<>();
        List<Hand> possibleOpponentHands = OpponentHandGenerator.generateAllPossibleHands(gameState.getUsedCards());

        for (Hand opponentHand : possibleOpponentHands) {
            int result = PokerComparator.compare(playerHand, opponentHand, board);
            if (result < 0) {
                List<Card> combined = new ArrayList<>();
                combined.addAll(opponentHand.getCards());
                combined.addAll(board.getCards());

                EvaluatedHand eval = PokerHandEvaluator.evaluate(combined);
                map.computeIfAbsent(eval.getType(), k -> new ArrayList<>()).add(opponentHand);
            }
        }

        return map;
    }
}
