package skat;

import java.util.Stack;

public class Deck {
    String type;
    private Card[] shownCards = new Card[8];
    private Card[] hiddenCards = new Card[8];

    public Deck(Stack<Card> cards, String type) {
        this.type = type;
        for(int i = 0; i < shownCards.length; i++) {
            shownCards[i] = cards.pop();
            hiddenCards[i] = cards.pop();
        }
    }

    public String getType(){
        return type;
    }

    public Card getCard(int index) {
        return shownCards[index];
    }
    public Card[] getCards() {
        return shownCards;
    }
    public Card getHiddenCard(int index) {
        return hiddenCards[index];
    }
    public Card[] getHiddenCards() {
        return hiddenCards;
    }
    public void setCard(int index, Card card) {
        shownCards[index] = card;
    }
    public void setHiddenCard(int index, Card card) {
        hiddenCards[index] = card;
    }

    public void moveCard(int i ){
        shownCards[i] = hiddenCards[i];
        hiddenCards[i] = null;
    }

    public Card takeCard(Card card){
        for(int i = 0; i < shownCards.length;i++){
            if(shownCards[i] != null) {
                if (shownCards[i].equals(card)) {
                    shownCards[i] = null;
                    return card;
                }
            }
        }
        return card;
    }

}
