package org.example.demo3.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.demo3.logic.DefeatAnalyzer;
import org.example.demo3.model.*;
import org.example.demo3.logic.PokerHandType;

import java.util.*;

public class PokerSimulatorUI {

    private final List<Card> selectedPlayerCards = new ArrayList<>();
    private final List<Card> selectedBoardCards = new ArrayList<>();
    private final VBox resultBox = new VBox(10);

    public void start(Stage stage) {
        stage.setTitle("Poker Defeat Analyzer");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label title = new Label("Alege cărțile tale și cele comune:");
        root.getChildren().add(title);

        VBox playerBox = createCardPicker("Cărțile tale (2)", selectedPlayerCards, 2);
        VBox boardBox = createCardPicker("Board (0-5)", selectedBoardCards, 5);

        Button simulateBtn = new Button("Simulează");

        simulateBtn.setOnAction(e -> {
            if (selectedPlayerCards.size() != 2) {
                resultBox.getChildren().clear();
                resultBox.getChildren().add(new Label("Selectează exact 2 cărți pentru tine."));
                return;
            }

            GameState gameState = new GameState(
                    new Hand(selectedPlayerCards.get(0), selectedPlayerCards.get(1)),
                    new Board(selectedBoardCards)
            );

            Map<PokerHandType, List<Hand>> handsByType = DefeatAnalyzer.getHandsThatBeatPlayerByType(gameState);
            resultBox.getChildren().clear();

            if (handsByType.isEmpty()) {
                resultBox.getChildren().add(new Label("Nicio mână nu te bate!"));
                return;
            }

            handsByType.keySet().stream()
                    .sorted(Comparator.reverseOrder())
                    .forEach(type -> {
                        VBox typeBox = new VBox(10);
                        typeBox.setPadding(new Insets(10));
                        typeBox.setStyle("-fx-border-color: #bbb; -fx-border-radius: 6; -fx-padding: 10;");

                        Label typeLabel = new Label(type.name());
                        typeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                        typeBox.getChildren().add(typeLabel);

                        HBox handsRow = new HBox(15);
                        handsRow.setPadding(new Insets(5));

                        List<Hand> hands = handsByType.get(type);
                        for (int i = 0; i < Math.min(5, hands.size()); i++) {
                            Hand hand = hands.get(i);

                            HBox handBox = new HBox(5);
                            handBox.setPadding(new Insets(5));
                            handBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 4; -fx-background-color: #f9f9f9;");

                            for (Card card : hand.getCards()) {
                                ImageView img = new ImageView(CardImageLoader.getCardImage(card));
                                img.setFitWidth(60);
                                img.setFitHeight(90);
                                handBox.getChildren().add(img);
                            }

                            handsRow.getChildren().add(handBox);
                        }

                        typeBox.getChildren().add(handsRow);
                        resultBox.getChildren().add(typeBox);
                    });
        });

        resultBox.setPadding(new Insets(10));

        root.getChildren().addAll(playerBox, boardBox, simulateBtn, new Separator(), resultBox);

        Scene scene = new Scene(new ScrollPane(root), 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createCardPicker(String label, List<Card> destination, int maxCount) {
        HBox box = new HBox(5);
        box.setPadding(new Insets(5));
        VBox wrapper = new VBox(new Label(label), box);
        wrapper.setSpacing(5);

        ComboBox<Rank> rankBox = new ComboBox<>();
        ComboBox<Suit> suitBox = new ComboBox<>();
        rankBox.getItems().addAll(Rank.values());
        suitBox.getItems().addAll(Suit.values());

        Button addBtn = new Button("Adaugă");
        Button resetBtn = new Button("Reset");

        addBtn.setOnAction(e -> {
            if (rankBox.getValue() != null && suitBox.getValue() != null) {
                Card card = new Card(rankBox.getValue(), suitBox.getValue());
                if (destination.contains(card) || selectedPlayerCards.contains(card) || selectedBoardCards.contains(card)) {
                    return;
                }
                if (destination.size() < maxCount) {
                    destination.add(card);
                    ImageView image = new ImageView(CardImageLoader.getCardImage(card));
                    image.setFitWidth(60);
                    image.setFitHeight(90);
                    box.getChildren().add(image);
                }
            }
        });

        resetBtn.setOnAction(e -> {
            destination.clear();
            box.getChildren().clear();
        });

        HBox controls = new HBox(5, rankBox, suitBox, addBtn, resetBtn);
        VBox container = new VBox(wrapper, controls);
        container.setPadding(new Insets(10, 0, 10, 0));
        return container;
    }
}
