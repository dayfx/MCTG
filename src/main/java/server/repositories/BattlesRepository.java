package server.repositories;

import application.Card;
import server.server.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattlesRepository {

    DatabaseConnection databaseConnection = new DatabaseConnection();

    public String executeBattle(String player1, String player2) {
        StringBuilder battlelog = new StringBuilder();

        List<Card> player1Deck = new ArrayList<>();
        List<Card> player2Deck = new ArrayList<>();

        try {
            Connection connection = databaseConnection.connect();

            // Get Player 1's Deck
            String queryPlayer1Deck = "SELECT * FROM cards WHERE owner = ? AND ready = true";
            PreparedStatement preparedStatementPlayer1Deck = connection.prepareStatement(queryPlayer1Deck);

            preparedStatementPlayer1Deck.setString(1, player1);

            ResultSet resultSetPlayer1Deck = preparedStatementPlayer1Deck.executeQuery();

            while (resultSetPlayer1Deck.next()) {
                player1Deck.add(new Card(
                        resultSetPlayer1Deck.getString("id"), // not needed but doesn't work otherwise
                        resultSetPlayer1Deck.getString("name"),
                        resultSetPlayer1Deck.getInt("damage"),
                        resultSetPlayer1Deck.getInt("packageid") // not needed but doesn't work otherwise
                ));
            }

            // Get Player 2's Deck

            String queryPlayer2Deck = "SELECT * FROM cards WHERE owner = ? AND ready = true";
            PreparedStatement preparedStatementPlayer2Deck = connection.prepareStatement(queryPlayer2Deck);

            preparedStatementPlayer2Deck.setString(1, player2);

            ResultSet resultSetPlayer2Deck = preparedStatementPlayer2Deck.executeQuery();

            while (resultSetPlayer2Deck.next()) {
                player2Deck.add(new Card(
                        resultSetPlayer2Deck.getString("id"), // not needed but doesn't work otherwise
                        resultSetPlayer2Deck.getString("name"),
                        resultSetPlayer2Deck.getInt("damage"),
                        resultSetPlayer2Deck.getInt("packageid") // not needed but doesn't work otherwise
                ));
            }

            // Show Player 1's deck
            battlelog.append(player1).append("'s Deck:\n");
            for (Card card : player1Deck) {
                battlelog.append("--Card-- ").append("\n")
                        .append("Name: ").append(card.getCardName()).append("\n")
                        .append("Damage: ").append(card.getDamage()).append("\n\n");
            }

            // Show Player 2's deck
            battlelog.append(player2).append("'s Deck:\n");
            for (Card card : player2Deck) {
                battlelog.append("--Card-- ").append("\n")
                        .append("Name: ").append(card.getCardName()).append("\n")
                        .append("Damage: ").append(card.getDamage()).append("\n\n");
            }

            battlelog.append("Battle begins between ").append(player1).append(" and ").append(player2).append("\n\n");

            int rounds = 0;
            Random random = new Random(); // to determine random cards

            while(!player1Deck.isEmpty() && !player2Deck.isEmpty() && rounds < 100) {
                rounds++; //increment round counter

                // pick random cards from both decks
                Card cardPlayer1 = player1Deck.get(random.nextInt(player1Deck.size()));
                Card cardPlayer2 = player2Deck.get(random.nextInt(player2Deck.size()));

                // describe round
                battlelog.append("\nRound: ").append(rounds).append("\n");
                battlelog.append(player1).append(" plays ").append(cardPlayer1.getCardName()).append(" (").append(cardPlayer1.getDamage()).append(" damage)\n");
                battlelog.append(player2).append(" plays ").append(cardPlayer2.getCardName()).append(" (").append(cardPlayer2.getDamage()).append(" damage)\n\n");

                // NO SPECIALTIES HAVE BEEN IMPLEMENTED UP TO THIS POINT
                if (isSpell(cardPlayer1.getCardName()) || isSpell(cardPlayer2.getCardName())){ // CHECK IF EITHER CARD IS A SPELL (no specialties)
                    double currentRoundDamagePlayer1 = cardPlayer1.getDamage(); // temporary var to calculate damage for this round specifically
                    double currentRoundDamagePlayer2 = cardPlayer2.getDamage(); // temporary var to calculate damage for this round specifically

                    // if player 1's card is a spell, calculate its damage onto player 2's card
                    if (isSpell(cardPlayer1.getCardName())) {
                        currentRoundDamagePlayer1 = applyElementEffect(cardPlayer1, cardPlayer2);
                    }
                    // if player 2's card is a spell, calculate its damage onto player 1's card
                    if (isSpell(cardPlayer2.getCardName())) {
                        currentRoundDamagePlayer2 = applyElementEffect(cardPlayer2, cardPlayer1);
                    }

                    // after new damage is calculated, compare damage like usual (duplicated code i guess but probably no time to optimise sadly
                    if (currentRoundDamagePlayer1 > currentRoundDamagePlayer2) {
                        battlelog.append("xx ").append(player1).append(" wins the round and takes ").append(cardPlayer2.getCardName()).append(" from ").append(player2).append(" xx\n");
                        player2Deck.remove(cardPlayer2);
                        player1Deck.add(cardPlayer2);
                        battlelog.append(player2).append(" now has ").append(player2Deck.size()).append(" cards left in their deck.\n\n");
                    } else if (currentRoundDamagePlayer2 > currentRoundDamagePlayer1) {
                        battlelog.append("xx ").append(player2).append(" wins the round and takes ").append(cardPlayer1.getCardName()).append(" from ").append(player1).append(" xx\n");
                        player1Deck.remove(cardPlayer1);
                        player2Deck.add(cardPlayer1);
                        battlelog.append(player1).append(" now has ").append(player1Deck.size()).append(" cards left in their deck.\n\n");
                    } else {
                        battlelog.append("Draw! No cards swapped!\n\n");
                    }
                } else { // IF NEITHER CARD IS A SPELL, PROCEED AS USUAL (no specialties)
                    // compare card's damage and transfer cards
                    if (cardPlayer1.getDamage() > cardPlayer2.getDamage()) {
                        battlelog.append("xx ").append(player1).append(" wins the round and takes ").append(cardPlayer2.getCardName()).append(" from ").append(player2).append(" xx\n");
                        player2Deck.remove(cardPlayer2); // remove card from round loser's deck
                        player1Deck.add(cardPlayer2);    // add card to round winner's deck

                        battlelog.append(player2).append(" now has ").append(player2Deck.size()).append(" cards left in their deck.\n\n"); // show number of left cards in deck
                    } else if (cardPlayer2.getDamage() > cardPlayer1.getDamage()) {
                        battlelog.append("xx ").append(player2).append(" wins the round and takes ").append(cardPlayer1.getCardName()).append(" from ").append(player1).append(" xx\n");
                        player1Deck.remove(cardPlayer1); // remove card from round loser's deck
                        player2Deck.add(cardPlayer1);    // add card to round winner's deck

                        battlelog.append(player1).append(" now has ").append(player1Deck.size()).append(" cards left in their deck.\n\n"); // show number of left cards in deck
                    } else {
                        battlelog.append("Draw! No cards swapped!\n\n");
                    }
                }
            }

            // game result
            if (player1Deck.isEmpty()) {
                battlelog.append("\n").append(player2).append(" wins the battle!\n");
            } else if (player2Deck.isEmpty()) {
                battlelog.append("\n").append(player1).append(" wins the battle!\n");
            } else {
                battlelog.append("\nBattle ended in a draw after 100 rounds.\n");
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return battlelog.toString(); // return full battle log
    }

    // HELPER METHODS BELOW

    // Method to determine if the card is a Spell
    private boolean isSpell(String cardName) {
        return cardName.contains("Spell");
    }

    // Method to derive the element from the card's name
    private String getElement(String cardName) {
        if (cardName.contains("Water")) {
            return "water";
        } else if (cardName.contains("Fire")) {
            return "fire";
        } else {
            return "normal";  // if card's name contains neither water nor fire, just return normal
        }
    }

    private double applyElementEffect(Card spellCard, Card opponentCard) {
        double modifiedDamage = spellCard.getDamage();

        String spellElement = getElement(spellCard.getCardName());  // get element from card's name
        String opponentElement = getElement(opponentCard.getCardName());  // get element from opponents card's name

        // check effectiveness
        if (spellElement.equals("water") && opponentElement.equals("fire")) {
            modifiedDamage *= 2;  // water beats fire
        } else if (spellElement.equals("fire") && opponentElement.equals("normal")) {
            modifiedDamage *= 2;  // fire beats normal
        } else if (spellElement.equals("normal") && opponentElement.equals("water")) {
            modifiedDamage *= 2;  // normal beats water
        } else if (spellElement.equals("fire") && opponentElement.equals("water")) {
            modifiedDamage /= 2;  // fire sucks into water
        } else if (spellElement.equals("water") && opponentElement.equals("normal")) {
            modifiedDamage /= 2;  // water sucks into normal
        }
        return modifiedDamage; // return this round's damage
    }
}
