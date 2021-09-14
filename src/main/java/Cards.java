package main.java;

import java.util.*;
import main.java.cardgame.*;

public class Cards {
    public static Map<String, Float> nameToVal = new HashMap<String, Float>() {{
            put("Ace", 11F);
            put("2", 2F);
            put("3", 3F);
            put("4", 4F);
            put("5", 5F);
            put("6", 6F);
            put("7", 7F);
            put("8", 8F);
            put("9", 9F);
            put("10", 10F);
            put("Jack", 10.001F);
            put("Queen", 10.01F);
            put("King", 10.1F);
    }};

    private static class Player {
        public List<PlayingCard> hand;
        public float score = 0;
        public boolean playedLastTurn = false;
        public Player(List<PlayingCard> hand) {
            this.hand = hand;
        }
    }

    public static int addLoopUp(int num, int increment, int min, int max) {
        int i = num + increment;
        if (i > max) {
            i = min;
        } else if (i < min) {
            i = max;
        }
        return i;
    }

    public static void main(String[] args) {

        // Ask for a number of players
        int numOfPlayers = ConsoleInputUtil.nextIntWithMessages(2, 5, "Welcome to Thirty-One!\nHow many players? (2 - 5)\n> ", "That's not a valid amount of players! Enter an amount from 2 to 5\n> ");
        System.out.println("Starting a " + numOfPlayers + " player game of Thirty-One.");

        // Initialize a deck and players
        Deck<PlayingCard> deck = DeckUtil.standard52CardDeck();
        deck.shuffle();

        Player[] players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = new Player(new ArrayList<>());
        }

        // Deal cards to the players
        for(int j = 0; j < 3; j++)  {
            for (int i = 0; i < numOfPlayers; i++) {
                players[i].hand.add(deck.draw());
            }
        }

        // Add the top card of the deck to the discard pile
        PlayingCard discardTop = deck.draw();

        boolean knocked = false;

        // Game loop
        while (true) {
            int numOfLastTurns = 0;
            for (int i = 0; i < numOfPlayers; i++) {

                // Player turn
                if (!players[i].playedLastTurn) {

                    // Build a list of all card names in the players hand
                    ArrayList<String> handNames = new ArrayList<>();
                    for(int k = 0; k < players[i].hand.size(); k++) {
                        handNames.add(players[i].hand.get(k).getDisplayName());
                    }

                    String handDisplay = String.join(", ", handNames);

                    // Create list of available actions
                    ArrayList<String> actions = new ArrayList<>();
                    actions.add("draw from the deck");
                    actions.add("draw from the discard pile");
                    if (knocked) {
                        System.out.println("This is your last turn!");
                    } else {
                        actions.add("knock");
                    }


                    System.out.println("Your hand is: " + handDisplay + "\nThe top card on the discard pile is: " + discardTop.getDisplayName());

                    // Action choice
                    int action = ConsoleInputUtil.actionListPrompt("Would you like to %s?\n> ", "That's not a valid action! Enter %s\n> ", actions);

                    // Knock action
                    if (action == 3) {
                        knocked = true;
                    }

                    // Draw from discard action
                    if (action == 2) {
                        // Draw
                        players[i].hand.add(discardTop);
                        System.out.println("You draw the " + discardTop.getDisplayName());

                        // Discard choice
                        handNames.add(discardTop.getDisplayName());
                        int cardIndex = ConsoleInputUtil.actionListPrompt("What card would you like to discard? %s\n> ", "That's not a valid card! %s\n> ", handNames);

                        // Discard
                        discardTop = players[i].hand.get(cardIndex - 1);
                        System.out.println("You discard a " + discardTop.getDisplayName());
                        players[i].hand.remove(cardIndex - 1);
                    }

                    // Draw from deck action
                    if (action == 1) {
                        // Draw
                        PlayingCard drawn = deck.draw();
                        players[i].hand.add(drawn);
                        System.out.println("You draw a " + drawn.getDisplayName());

                        // Discard choice
                        handNames.add(drawn.getDisplayName());
                        int cardIndex = ConsoleInputUtil.actionListPrompt("What card would you like to discard? %s\n> ", "That's not a valid card! %s\n> ", handNames);

                        // Discard
                        discardTop = players[i].hand.get(cardIndex - 1);
                        System.out.println("You discard a " + discardTop.getDisplayName());
                        players[i].hand.remove(cardIndex - 1);
                    }

                    // If this is the players last turn
                    if (knocked) {
                        players[i].playedLastTurn = true;
                        numOfLastTurns += 1;

                        // Score player
                        PlayingCard[] hand = new PlayingCard[]{players[i].hand.get(0), players[i].hand.get(1), players[i].hand.get(2)};
                        if (hand[0].value.equals(hand[1].value) && hand[0].value.equals(hand[2].value)) {
                            players[i].score = 30.5F;
                        } else {
                            for (int j = 0; j < 3; j++) {
                                Float score = nameToVal.get(hand[j].value);
                                int k = addLoopUp(j, 1, 0, 2);
                                int l = addLoopUp(j, -1, 0, 2);

                                if (hand[j].suit.equals(hand[k].suit)) {
                                    score += nameToVal.get(hand[k].value);
                                }
                                if (hand[j].suit.equals(hand[l].suit)) {
                                    score += nameToVal.get(hand[l].value);
                                }
                                if (score > players[i].score) {
                                    players[i].score = score;
                                }
                            }
                        }
                    }
                }
            }

            if (numOfLastTurns == numOfPlayers) {
                break;
            }
        }

        // Find winners
        float highest = 0.0F;
        List<Integer> winners = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++) {
            if (players[i].score == highest) {
                winners.add(i);
            } else if (players[i].score > highest) {
                highest = players[i].score;
                winners.clear();
                winners.add(i);
            }
        }

        // Announce winner
        if (winners.size() == 1) {
            int i = winners.get(0);
            System.out.println("Player " + (i + 1) + " wins with a score of: " + Math.round(players[i].score));
        } else {
            StringBuilder winnersAnnouncement = new StringBuilder();
            for (int i = 0; i < (winners.size() - 1); i++) {
                winnersAnnouncement.append("Player ").append(i + 1).append(", ");
            }
            winnersAnnouncement.append("and Player ").append(winners.size()).append(" all tied with a score of ").append(Math.round(players[0].score));
            System.out.println(winnersAnnouncement);
        }

    }
}
