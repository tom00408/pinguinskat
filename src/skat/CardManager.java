package skat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class CardManager {
    final String[] suits = {"Kreuz", "Peak", "Herz", "Karo"};
    final String[] ranks = {"7", "8", "9", "10", "Jack", "Queen", "King","Ace"};

    public final int kartenBreite = 60;
    public final int kartenHoehe = 89;


    ArrayList<Card> cards = new ArrayList<>();
    public BufferedImage[] images = new BufferedImage[32];


    Stack<Card> cardStack1 = new Stack<>();
    Stack<Card> cardStack2 = new Stack<>();

    public CardManager() {
        loadImages();
        createCards();
        shuffle();

        //printStacks();

    }

    public void createCards(){
        int i = 0;
        for (String suit : suits){
            for (String rank : ranks){
                Card c = new Card(suit+"_"+rank,suit,rank,images[i] );
                c.setSize(kartenBreite, kartenHoehe);
                c.setShown(false);
               // System.out.println(c.getName());
                cards.add(c);
                i++;
            }
        }
    }

    public void loadImages(){

        int eckeX = 1+5*kartenBreite;
        int eckeY = 1;

        BufferedImage karten = null;
        try{
            karten = ImageIO.read(getClass().getResourceAsStream("/karten2.jpeg"));
        }
        catch(IOException e){
            e.printStackTrace();
        }


        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 8; j++){
                //System.out.println(i + " "+j);
                images[i*8+j] = karten.getSubimage(eckeX+j*kartenBreite,eckeY+i*kartenHoehe,kartenBreite, kartenHoehe);
            }
        }

    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        // Neues BufferedImage mit Zielgröße erstellen
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());

        // Graphics2D-Objekt aus dem neuen Bild holen
        Graphics2D g2d = resizedImage.createGraphics();

        // Ursprüngliches Bild mit Skalierung in das neue Bild zeichnen
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);

        // Ressourcen des Graphics2D-Objekts freigeben
        g2d.dispose();

        return resizedImage;
    }

    public void shuffle(){
        while(cards.size() > 1){
            int i = (int) (Math.random()*cards.size());
            cardStack1.push(cards.get(i));
            cards.remove(i);
            i = (int) (Math.random()*cards.size());
            cardStack2.push(cards.get(i));
            cards.remove(i);
        }
    }

    public Stack<Card> getCardStack1(){
        return cardStack1;
    }
    public Stack<Card> getCardStack2(){
        return cardStack2;
    }

    public void printStacks(){
        System.out.println("STACK 1");
        for(Card card : cardStack1){
            System.out.println(card.name);
        }
        System.out.println("STACK 2");
        for(Card card : cardStack2){
            System.out.println(card.name);
        }
    }



}
