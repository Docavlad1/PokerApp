package org.example.demo3.ui;

import javafx.scene.image.Image;
import org.example.demo3.model.Card;
import org.example.demo3.model.Rank;
import org.example.demo3.model.Suit;

import java.util.HashMap;
import java.util.Map;

public class CardImageLoader {

    private static final Map<String, Image> imageCache = new HashMap<>();

    public static Image getCardImage(Card card) {
        String imageName = getImageFileName(card);
        return imageCache.computeIfAbsent(imageName, name -> {
            try {
                return new Image(CardImageLoader.class.getResourceAsStream("/Cards/" + name));
            } catch (Exception e) {
                System.err.println("Nu s-a găsit imaginea pentru carte: " + name);
                return null;
            }
        });
    }

    private static String getImageFileName(Card card) {
        return formatRank(card.getRank()) + "_of_" + formatSuit(card.getSuit()) + ".png";
    }

    private static String formatRank(Rank rank) {
        switch (rank) {
            case TWO -> { return "2"; }
            case THREE -> { return "3"; }
            case FOUR -> { return "4"; }
            case FIVE -> { return "5"; }
            case SIX -> { return "6"; }
            case SEVEN -> { return "7"; }
            case EIGHT -> { return "8"; }
            case NINE -> { return "9"; }
            case TEN -> { return "10"; }
            case JACK -> { return "jack"; }
            case QUEEN -> { return "queen"; }
            case KING -> { return "king"; }
            case ACE -> { return "ace"; }
            default -> throw new IllegalArgumentException("Rang necunoscut: " + rank);
        }
    }

    private static String formatSuit(Suit suit) {
        switch (suit) {
            case CLUBS -> { return "clubs"; }
            case DIAMONDS -> { return "diamonds"; }
            case HEARTS -> { return "hearts"; }
            case SPADES -> { return "spades"; }
            default -> throw new IllegalArgumentException("Suit necunoscut: " + suit);
        }
    }
}
