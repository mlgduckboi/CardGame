package main.java.cardgame;

public class PlayingCard implements Card {
    public String suit;
    public String value;
    public PlayingCard(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getDisplayName() {
        return this.value + " of " + this.suit;
    }
}
