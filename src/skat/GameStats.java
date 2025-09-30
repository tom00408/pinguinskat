package skat;

import java.util.ArrayList;
import java.util.Stack;

public class GameStats {

    Stack<Card> playerJacks;
    Stack<Card> botJacks;

    OUTCOME starter;



    public GameStats(){
        playerJacks = new Stack<>();
        botJacks = new Stack<>();
    }

    public void setJacks(Deck playerDeck, Deck botDeck){
        for(Card card : playerDeck.getCards()){
            if(card.getRank().equals("Jack")){
                playerJacks.push(card);
            }
        }
        for(Card card : playerDeck.getHiddenCards()){
            if(card.getRank().equals("Jack")){
                playerJacks.push(card);
            }
        }for(Card card : botDeck.getCards()){
            if(card.getRank().equals("Jack")){
                botJacks.push(card);
            }
        }for(Card card : botDeck.getHiddenCards()){
            if(card.getRank().equals("Jack")){
                botJacks.push(card);
            }
        }

    }


    public void setStarter(OUTCOME s){
        this.starter = s;
    }

    public Stack<Card> getPlayerJacks(){
        return playerJacks;
    }
    public Stack<Card> getBotJacks() {
        return botJacks;
    }

    public OUTCOME getStarter(){
        return starter;
    }

    public String getMit(){
        for(Card card: playerJacks){
            if(card.getSuit().equals("Kreuz")){
                return "Mit ";
            }
        }
        return "Ohne ";
    }

    public int getSpitze(){
        ArrayList<String> list = new ArrayList<>();
        for(Card card: playerJacks){
            list.add(card.getSuit());
        }

        if(list.contains("Kreuz")){
            if(list.contains("Peak")){
                if(list.contains("Herz")){
                    if(list.contains("Karo")){
                        return 4;
                    }else {
                        return 3;
                    }
                }else{
                    return 2;
                }
            }else{
                return 1;
            }
        }else{
            if(!list.contains("Peak")){
                if(!list.contains("Herz")){
                    if(!list.contains("Karo")){
                        return 4;
                    }else {
                        return 3;
                    }
                }else{
                    return 2;
                }
            }else{
                return 1;
            }
        }



    }

}
