package main.java;


import java.util.*;

public class Cards {
    public static String[] suites = new String[]{"Hearts","Clubs","Diamonds","Spades"};
    public static String[] values = new String[]{"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
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

    private static class Card {
        public String suit;
        public String value;
        public Card(String suit, String value) {
            this.suit = suit;
            this.value = value;
        }

        public String getDisplayName() {
            return this.value + " of " + this.suit;
        }
    }

    private static class Player {
        public List<Card> hand;
        public float score = 0;
        public boolean playedLastTurn = false;
        public Player(List<Card> hand) {
            this.hand = hand;
        }
    }

    private static class Deck {
        public static Stack<Card> deck = new Stack<Card>();

        public Deck(String[] suites, String[] values) {
            for (String suite : suites) {
                for (String value : values) {
                    this.deck.push(new Card(suite, value));
                }
            }
            Collections.shuffle(this.deck);
            Collections.shuffle(this.deck);
            Collections.shuffle(this.deck);
        }

        public static Card draw() {
            return deck.pop();
        }
    }
    public static List<String> getNamesOfCards(List<Card> cards) {
        List<String> names = new ArrayList<String>();
        for(int k = 0; k < cards.size(); k++) {
            names.add(cards.get(k).getDisplayName());
        }
        return names;
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

    public static void main(String args[]) throws Exception {
        Scanner inputTracker = new Scanner( System.in );

        System.out.print("Welcome to Thirty-One!\nHow many players? (2 - 5)\n> ");
        int numOfPlayers = inputTracker.nextInt();

        while (!(numOfPlayers >= 2 && numOfPlayers <= 5)) {
            System.out.print("That's not a valid amount of players! Enter an amount from 2 to 5\n> ");
            numOfPlayers = inputTracker.nextInt();
        }
        System.out.println("Starting a " + numOfPlayers + " player game of Thirty-One.");

        Deck deck = new Deck(suites, values);
        Player[] players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = new Player(new ArrayList<Card>());
        }


        for(int j = 0; j < 3; j++)  {
            for (int i = 0; i < numOfPlayers; i++) {
                players[i].hand.add(deck.draw());
            }
        }

        Card discardTop = Deck.draw();

        boolean gameEnd = false;
        boolean knocked = false;
        while (!gameEnd) {
            int numOfLastTurns = 0;
            for (int i = 0; i < numOfPlayers; i++) {
                if (!players[i].playedLastTurn) {
                    //clear screen
                    List<String> handNames = getNamesOfCards(players[i].hand);
                    String handDisplay = String.join(", ", handNames);
                    String actions = "[1] draw from the deck, ";
                    if (knocked) {
                        System.out.println("This is your last turn!");

                        actions += "or [2] draw from the discard pile";
                    } else {
                        actions += "[2] draw from the discard pile, or [3] knock";
                    }
                    System.out.print("Your hand is: " + handDisplay +
                            "\nThe top card on the discard pile is: " + discardTop.getDisplayName() + "\nWould you like to " + actions + "?\n> ");
                    int action = inputTracker.nextInt();

                    while (!(action == 1 || action == 2 || (action == 3 && !knocked))) {
                        System.out.print("That's not a valid action! " + actions + "\n> ");
                        action = inputTracker.nextInt();
                    }
                    if (action == 3) {
                        knocked = true;
                    }
                    if (action == 2) {
                        players[i].hand.add(discardTop);
                        handNames = getNamesOfCards(players[i].hand);
                        System.out.print("You draw a " + discardTop.getDisplayName() +
                                "\nWhat card would you like to discard? [1] " + handNames.get(0) +
                                " [2] " + handNames.get(1) +
                                " [3] " + handNames.get(2) +
                                " [4] " + handNames.get(3) + "\n> ");
                        int cardIndex = inputTracker.nextInt();
                        while (!(cardIndex > 0 && cardIndex <= handNames.size())) {
                            System.out.print("That's not a valid card! " + handNames.get(0) +
                                    " [2] " + handNames.get(1) +
                                    " [3] " + handNames.get(2) +
                                    " [4] " + handNames.get(3) + "\n> ");
                            cardIndex = inputTracker.nextInt();
                        }

                        discardTop = players[i].hand.get(cardIndex - 1);
                        players[i].hand.remove(cardIndex - 1);
                        System.out.println("You discard a " + discardTop.getDisplayName());
                    }
                    if (action == 1) {
                        Card drawn = deck.draw();
                        players[i].hand.add(drawn);
                        handNames = getNamesOfCards(players[i].hand);
                        System.out.print("You draw a " + drawn.getDisplayName() +
                                "\nWhat card would you like to discard? [1] " + handNames.get(0) +
                                " [2] " + handNames.get(1) +
                                " [3] " + handNames.get(2) +
                                " [4] " + handNames.get(3) + "\n> ");
                        int cardIndex = inputTracker.nextInt();
                        while (!(cardIndex > 0 && cardIndex <= handNames.size())) {
                            System.out.print("That's not a valid card! " + handNames.get(0) +
                                    " [2] " + handNames.get(1) +
                                    " [3] " + handNames.get(2) +
                                    " [4] " + handNames.get(3) + "\n> ");
                            cardIndex = inputTracker.nextInt();
                        }

                        discardTop = players[i].hand.get(cardIndex - 1);
                        System.out.println("You discard a " + discardTop.getDisplayName());

                        players[i].hand.remove(cardIndex - 1);

                    }

                    if (knocked) {
                        players[i].playedLastTurn = true;
                        numOfLastTurns += 1;
                        Card[] hand = new Card[]{players[i].hand.get(0), players[i].hand.get(1), players[i].hand.get(2)};
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
                gameEnd = true;
            }
        }


        Float highest = 0.0F;
        List<Integer> winners = new ArrayList<Integer>();
        for (int i = 0; i < numOfPlayers; i++) {
            if (players[i].score == highest) {
                winners.add(i);
            } else if (players[i].score > highest) {
                highest = players[i].score;
                winners.clear();
                winners.add(i);
            }
            //System.out.println("Player " + (i + 1) + " has a score of: " + players[i].score);
        }
        if (winners.size() == 1) {
            int i = winners.get(0);
            System.out.println("Player " + (i + 1) + " wins with a score of: " + Math.round(players[i].score));
        } else {
            String winnersAnnouncement = "";
            for (int i = 0; i < (winners.size() - 1); i++) {
                winnersAnnouncement += "Player " + (i + 1) + ", ";
            }
            winnersAnnouncement += "and Player " + winners.size() + " all tied with a score of " + Math.round(players[0].score);
            System.out.println(winnersAnnouncement);
        }

    }
}
