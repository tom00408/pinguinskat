package skat;

import java.awt.image.BufferedImage;

public class Card {
    String name;
    BufferedImage image;
    int value;
    String suit;
    String rank;

    boolean shown;
    int x,y,w,h;

    public Card(String name, String suit, String rank, BufferedImage image) {
        this.name = name;
        this.suit = suit;
        this.rank = rank;
        this.image = image;

        int v;
        switch (rank) {
            case "Jack":
                v = 2;
                break;
            case "Queen":
                v = 3;
                break;
            case "King":
                v = 4;
                break;
            case "Ace":
                v = 11;
                break;
            case "10":
                v = 10;
                break;
            default:
                v = 0;
                break;
        }
        this.value = v;
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getW(){
        return w;
    }
    public int getH(){
        return h;
    }
    public boolean isShown(){
        return shown;
    }

    public BufferedImage getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
    public String getSuit() {
        return suit;
    }
    public String getRank() {
        return rank;
    }
    public int getValue() {
        return value;
    }

    public int getPostion(String trumph, String farbe){
        if(rank.equals("Jack")){
            switch(suit){
                case "Kreuz":
                    return 1;
                case "Peak":
                    return 2;
                case "Herz":
                    return 3;
                case "Karo":
                    return 4;
                default:
                    System.out.println("Error finding Jacks suit");
                    break;
            }
        } else if (suit.equals(trumph)) {
            switch (rank){
                case "Ace":
                    return 5;
                case "10":
                    return 6;
                case "King":
                    return 7;
                case "Queen":
                    return 8;
                case "9":
                    return 9;
                case "8":
                    return 10;
                case "7":
                    return 11;
            }
        } else if (suit.equals(farbe)) {
            switch (rank){
                case "Ace":
                    return 5+7;
                case "10":
                    return 6+7;
                case "King":
                    return 7+7;
                case "Queen":
                    return 8+7;
                case "9":
                    return 9+7;
                case "8":
                    return 10+7;
                case "7":
                    return 11+7;
            }
        }
        return 69;
    }
}
