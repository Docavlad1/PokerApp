package org.example.demo3.model;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final Hand playerHand;
    private final Board board;

    public GameState(Hand playerHand, Board board) {
        this.playerHand = playerHand;
        this.board = board;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Board getBoard() {
        return board;
    }

    public List<Card> getUsedCards() {
        List<Card> used = new ArrayList<>(board.getCards());
        used.addAll(playerHand.getCards());
        return used;
    }
}
