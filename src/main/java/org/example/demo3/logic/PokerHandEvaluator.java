package org.example.demo3.logic;

import org.example.demo3.model.Card;
import org.example.demo3.model.Rank;
import org.example.demo3.model.Suit;

import java.util.*;

public class PokerHandEvaluator {

    public static EvaluatedHand evaluate(List<Card> cards) {
        // 1. Generăm toate combinațiile de 5 cărți din cele 5-7 date
        List<List<Card>> allFiveCardHands = generateCombinations(cards, 5);
        EvaluatedHand best = null;

        for (List<Card> hand : allFiveCardHands) {
            EvaluatedHand current = evaluateFiveCards(hand);
            if (best == null || current.compareTo(best) > 0) {
                best = current;
            }
        }

        return best;
    }

    private static EvaluatedHand evaluateFiveCards(List<Card> cards) {
        // ordonăm cărțile descrescător
        cards.sort(Comparator.comparingInt((Card c) -> c.getRank().getValue()).reversed());

        boolean flush = isFlush(cards);
        boolean straight = isStraight(cards);
        Map<Rank, Integer> rankCounts = countRanks(cards);
        List<Integer> counts = new ArrayList<>(rankCounts.values());
        Collections.sort(counts, Collections.reverseOrder());

        PokerHandType type;

        if (flush && straight) {
            type = cards.get(0).getRank() == Rank.ACE ? PokerHandType.ROYAL_FLUSH : PokerHandType.STRAIGHT_FLUSH;
        } else if (counts.get(0) == 4) {
            type = PokerHandType.FOUR_OF_A_KIND;
        } else if (counts.get(0) == 3 && counts.size() > 1 && counts.get(1) >= 2) {
            type = PokerHandType.FULL_HOUSE;
        } else if (flush) {
            type = PokerHandType.FLUSH;
        } else if (straight) {
            type = PokerHandType.STRAIGHT;
        } else if (counts.get(0) == 3) {
            type = PokerHandType.THREE_OF_A_KIND;
        } else if (counts.get(0) == 2 && counts.size() > 1 && counts.get(1) == 2) {
            type = PokerHandType.TWO_PAIR;
        } else if (counts.get(0) == 2) {
            type = PokerHandType.ONE_PAIR;
        } else {
            type = PokerHandType.HIGH_CARD;
        }

        int score = calculateScore(type, cards);
        return new EvaluatedHand(type, score);
    }

    private static boolean isFlush(List<Card> cards) {
        Suit suit = cards.get(0).getSuit();
        return cards.stream().allMatch(c -> c.getSuit() == suit);
    }

    private static boolean isStraight(List<Card> cards) {
        List<Integer> values = cards.stream().map(c -> c.getRank().getValue()).distinct().sorted().toList();
        if (values.size() < 5) return false;
        for (int i = 0; i <= values.size() - 5; i++) {
            boolean straight = true;
            for (int j = 0; j < 4; j++) {
                if (values.get(i + j) + 1 != values.get(i + j + 1)) {
                    straight = false;
                    break;
                }
            }
            if (straight) return true;
        }
        // special case: A-2-3-4-5
        return values.containsAll(List.of(14, 2, 3, 4, 5));
    }

    private static Map<Rank, Integer> countRanks(List<Card> cards) {
        Map<Rank, Integer> map = new HashMap<>();
        for (Card c : cards) {
            map.put(c.getRank(), map.getOrDefault(c.getRank(), 0) + 1);
        }
        return map;
    }

    private static int calculateScore(PokerHandType type, List<Card> cards) {
        int typeScore = type.ordinal() * 1_000_000;
        int kickerScore = 0;
        int multiplier = 1;
        for (Card c : cards) {
            kickerScore += c.getRank().getValue() * multiplier;
            multiplier *= 15;
        }
        return typeScore + kickerScore;
    }

    private static List<List<Card>> generateCombinations(List<Card> cards, int k) {
        List<List<Card>> result = new ArrayList<>();
        combine(cards, k, 0, new ArrayList<>(), result);
        return result;
    }

    private static void combine(List<Card> cards, int k, int start, List<Card> path, List<List<Card>> result) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < cards.size(); i++) {
            path.add(cards.get(i));
            combine(cards, k, i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }

    public static class EvaluatedHand implements Comparable<EvaluatedHand> {
        private final PokerHandType type;
        private final int score;

        public EvaluatedHand(PokerHandType type, int score) {
            this.type = type;
            this.score = score;
        }

        public PokerHandType getType() {
            return type;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(EvaluatedHand o) {
            return Integer.compare(this.score, o.score);
        }

        @Override
        public String toString() {
            return type + " (score: " + score + ")";
        }
    }
}
