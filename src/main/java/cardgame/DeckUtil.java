package main.java.cardgame;

public class DeckUtil {
    static final String[] standardSuits = new String[]{"Hearts","Clubs","Diamonds","Spades"};
    static final String[] standardValues = new String[]{"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};

    public static Deck<PlayingCard> populatedDeck(String[] suits, String[] values) {
        Deck<PlayingCard> deck = new Deck<>();
        for (String suit : suits) {
            for (String value : values) {
                deck.getArrayList().add(new PlayingCard(suit, value));
            }
        }
        return deck;
    }

    public static Deck<PlayingCard> standard52CardDeck() {
        return populatedDeck(standardSuits, standardValues);
    }


}
