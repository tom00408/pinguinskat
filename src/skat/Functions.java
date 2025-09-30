package skat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public class Functions {
    public Functions(){

    }

    public boolean validKeyCode(int keyCode){
        return (keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90 ||keyCode == 32);
    }

    public void reactionTime(int sekunden){
        try{
            Thread.sleep(sekunden * 1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    public void sleeper(int ms){
        try{
            Thread.sleep(ms);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public int getPoints(Stack<Card> stack){

        
        int points = 0;
        for(Card card : stack){
            points += card.getValue();
        }
        return points;

    }
    public int getSpielwert(String trumph){
        switch (trumph){
            case "Kreuz":
                return 12;
            case "Peak":
                return 11;
            case "Herz":
                return 10;
            case "Karo":
                return 9;
            case "Grand":
                return 24;
            default:
                return 0;
        }
    }

    public String getTime(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return currentTime.format(formatter);
    }

    public boolean checkForSuit(Card[] auslage, String farbe){
        for(Card card : auslage){
            if(card != null) {
                if (card.getSuit().equals(farbe) && !card.getRank().equals("Jack")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean chechForRank(Card[] auslage, String rank){
        for(Card card : auslage){
            if(card != null){
                if(card.getRank().equals(rank)){
                    return  true;
                }
            }
        }
        return false;
    }

    public boolean checkForTrumph(Card[] auslage, String trumpph){
        for(Card card : auslage){
            if(card != null){
                if(card.getRank().equals("Jack") || card.getSuit().equals(trumpph))
                    return true;
            }
        }
        return false;
    }

    public boolean checkForCard(Card[] auslage, String farbe, String rank){
        for(Card card: auslage){
            if(card != null){
                if(card.getRank().equals(rank) && card.getSuit().equals(farbe))
                    return true;
            }
        }
        return false;
    }

    public int besteAusmDeck(Card[] auslage,String trumph){
        int min = 69;
        for(Card card : auslage){
            if(card != null) {
                int n = card.getPostion(trumph, trumph);
                min = n < min ? n : min;
            }
        }
        return min;
    }


}


