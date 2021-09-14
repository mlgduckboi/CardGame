package main.java.cardgame;

import java.util.ArrayList;
import java.util.Collections;

public class Deck<T> {
   private ArrayList<T> deck = new ArrayList<T>();
   public Deck() {}
   public Deck(ArrayList<T> deck) {
       this.deck = deck;
   }

   public T draw() {
       return this.deck.remove(this.deck.size() - 1);
   }

    public void shuffle() {
        Collections.shuffle(this.deck);
        Collections.shuffle(this.deck);
        Collections.shuffle(this.deck);
    }

    public ArrayList<T> getArrayList() {
       return this.deck;
    }
}
