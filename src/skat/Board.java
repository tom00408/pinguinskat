package skat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

enum PHASES {
    SELECT_NAME,
    SELECT_TRUMPH,
    PLAYING,
    RESULT,
    HIGHSCORELIST
}

enum OUTCOME {
    PLAYER,
    BOT
}

enum PLAINGPHASES{
    FIRST_CARD,
    SECOND_CARD,
    EVALUATE
}

enum DIFFICULTY{
    EASY,
    MEDIUM,
    HARD

}


public class Board extends JPanel {

    public boolean running = true;

    CardManager cardManager;
    GameStats gameStats;
    ScoreboardManager scoreboardManager;
    Functions f = new Functions();
    Random r = new Random();

    PHASES phase;

    String botName;
    String playerName;

    Color playerColor = Color.RED;
    int playerScore;
    String[] statLine;
    boolean statLineAdded = false;

    DIFFICULTY difficulty;

    String trumph = "";

    String note1 = "";

    int playerPoints;
    int botPoints;

    boolean playerPointsShown;
    boolean botPointsShown;

    boolean playerStarts;
    boolean kontra;
    boolean playerMoveRequested;

    boolean zurückZumSpiel;

    PLAINGPHASES playingPhase;

    int round;

    Deck playerDeck;
    Deck botDeck;

    Stack<Card> playersWonCards = new Stack<>();
    Stack<Card> botsWonCards = new Stack<>();

    List<ClickableBox>  selectNamesBoxen= new ArrayList<>();
    List<ClickableBox> resultBoxen = new ArrayList<>();
    List<ClickableBox> highscoreBoxen = new ArrayList<>();
    List<ClickableBox> difficultyBoxen = new ArrayList<>();
    List<ClickableBox> getSelectNamesBoxen = new ArrayList<>();
    List<ClickableBox> allTimeBoxen = new ArrayList<>();

    OUTCOME winner;

    Card playersSelectedCard;
    Card botsSelectedCard;

    BufferedImage rueckseite;
    BufferedImage icon;


    public Board() {
        this.playerName = "";
        cardManager = new CardManager();
        gameStats = new GameStats();
        scoreboardManager = new ScoreboardManager();

        this.playerDeck = new Deck(cardManager.getCardStack1(), "player");
        this.botDeck = new Deck(cardManager.getCardStack2(), "bot");

        giveCords(playerDeck);
        giveCords(botDeck);

        gameStats.setJacks(playerDeck,botDeck);


        setDefaultValues();
        createBoxes();

    }

    public void giveCords(Deck deck) {
        int startX = 500;
        int startYbot = 100;
        int startYplayer = 500;

        int gapX = 100 + cardManager.kartenBreite;
        int gapY = 40 + cardManager.kartenHoehe;

        switch (deck.getType()) {
            case "bot":
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 4; j++) {
                        deck.getCards()[i * 4 + j].setCoordinates(startX + j * gapX, startYbot + i * gapY);
                        deck.getCards()[i * 4 + j].setShown(true);
                        deck.getHiddenCards()[i * 4 + j].setCoordinates(startX + j * gapX, startYbot + i * gapY);

                    }
                }

                break;
            case "player":
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 4; j++) {
                        deck.getCards()[i * 4 + j].setCoordinates(startX + j * gapX, startYplayer + i * gapY);
                        deck.getCards()[i * 4 + j].setShown(true);
                        deck.getHiddenCards()[i * 4 + j].setCoordinates(startX + j * gapX, startYplayer + i * gapY);

                    }
                }
                break;
            default:
                System.out.println("deck type nicht erkannt -> " + deck.getType());
                break;
        }
    }

    public void setDefaultValues() {

        this.phase = PHASES.SELECT_NAME;
        this.difficulty = DIFFICULTY.HARD;

        this.botName = "Beta";
        this.playerName = "";

        this.playerPoints = 0;
        this.botPoints = 0;

        this.playerPointsShown = true;
        this.botPointsShown = true;

        this.zurückZumSpiel = false;

        this.playerMoveRequested = false;
        //this.playerStarts = false;
        this.playerStarts = r.nextBoolean();
        this.playingPhase = PLAINGPHASES.FIRST_CARD;

        gameStats.setStarter(playerStarts ? OUTCOME.PLAYER : OUTCOME.BOT);


        this.round = 1;

        try {
            rueckseite = ImageIO.read(getClass().getResourceAsStream("/rückseite.png"));
            icon = ImageIO.read(getClass().getResourceAsStream("/icon.png"));
        } catch (IOException e) {
            System.out.println("Biler nicht geladen 1");
        }
    }

    public void createBoxes(){
        ClickableBox box1 = new ClickableBox("Zum Scoreboard",335,690,70,230,Color.GRAY);
        ClickableBox box2 = new ClickableBox("   Neues Spiel ",835,690,70,230,Color.GRAY);
        resultBoxen.add(box1);
        resultBoxen.add(box2);

        ClickableBox box3 = new ClickableBox("Zurück zum Spiel",335,690,70,230,Color.GRAY);
        highscoreBoxen.add(box2);
        highscoreBoxen.add(box3);

        ClickableBox box_easy = new ClickableBox("  Easy",360,450,70,120,Color.GREEN);
        ClickableBox box_medium = new ClickableBox("Medium",640,450,70,120,Color.YELLOW);
        ClickableBox box_hard = new ClickableBox("  Hard",920,450,70,120,Color.RED);
        difficultyBoxen.add(box_easy);
        difficultyBoxen.add(box_medium);
        difficultyBoxen.add(box_hard);

        ClickableBox box4 = new ClickableBox("Zum Scoreboard",585,690,70,230,Color.GRAY);
        selectNamesBoxen.add(box4);

        ClickableBox exit = new ClickableBox("exit",1350,20,30,30,Color.GRAY);
        allTimeBoxen.add(exit);
    }


    public void getMouseClick(int clickX, int clickY) {

        for(ClickableBox box : allTimeBoxen){
            if(box.clicked(clickX,clickY)){
                switch (box.text){
                    case "exit":
                        running = false;
                }
            }
        }

        switch (phase) {
            case SELECT_NAME:
                for(ClickableBox box : difficultyBoxen){
                    if(box.clicked(clickX,clickY)){
                        switch (box.text){
                            case "  Easy":
                                difficulty = DIFFICULTY.EASY;
                                break;
                            case "Medium":
                                difficulty = DIFFICULTY.MEDIUM;
                                break;
                            case "  Hard":
                                difficulty = DIFFICULTY.HARD;
                                break;
                        }
                    }
                }
                for(ClickableBox box: selectNamesBoxen){
                    if(box.clicked(clickX,clickY)){
                        switch (box.text){
                            case "Zum Scoreboard":
                                phase = PHASES.HIGHSCORELIST;
                                zurückZumSpiel = false;
                                break;
                        }
                    }
                }
                break;
            case SELECT_TRUMPH:
                for (int i = 4; i < 8; i++) {
                    Card card = playerDeck.getCards()[i];
                    //System.out.println(card.getName());
                        if (clickX >= card.getX()
                                && clickX <= card.getX() + card.getW()
                                && clickY >= card.getY()
                                && clickY <= card.getY() + card.getH()
                        ) {
                            System.out.println("trumph gewählt");
                            if(card.getRank().equals("Jack")){
                                trumph = "Grand";
                            }else{
                                trumph = card.getSuit();
                            }
                            phase = PHASES.PLAYING;
                            System.out.println("PLAYING STARTS");
                        }

                }
                break;
            case PLAYING:
                if (playerMoveRequested) {
                    for (Card card : playerDeck.getCards()) {
                        if(card != null && isCardValidPick(card,OUTCOME.PLAYER)) {
                            if (clickX >= card.getX()
                                    && clickX <= card.getX() + card.getW()
                                    && clickY >= card.getY()
                                    && clickY <= card.getY() + card.getH()) {
                                playersSelectedCard = playerDeck.takeCard(card);
                                System.out.println("Player Selected : " + playersSelectedCard.getName());
                                playerMoveRequested = false;
                            }
                        }
                    }



                }
                break;
            case RESULT:
                for(ClickableBox box : resultBoxen){
                    if(clickX >= box.x1
                    && clickX <= box.x2
                    && clickY >= box.y1
                    && clickY <= box.y2
                    ){
                        switch (box.text){
                            case "Zum Scoreboard":
                                phase = PHASES.HIGHSCORELIST;
                                zurückZumSpiel = true;
                                break;
                            case "   Neues Spiel ":
                                newGame();
                        }
                    }
                }
                break;
            case HIGHSCORELIST:
                for(ClickableBox box : highscoreBoxen){
                    if(box.clicked(clickX,clickY)){
                        switch (box.text){
                            case "Zurück zum Spiel":
                                if(zurückZumSpiel)
                                    phase = PHASES.RESULT;
                                break;
                            case "   Neues Spiel ":
                                newGame();
                        }
                    }
                }
                break;
                //System.out.println("getMouseClick called ");
        }
    }


    public void getKeypress(char keyChar, int keyCode) {
        if (phase.equals(PHASES.SELECT_NAME)) {
            if (keyCode == KeyEvent.VK_BACK_SPACE && playerName.length() > 0) {
                playerName = playerName.substring(0, playerName.length() - 1);
            } else if (keyCode == KeyEvent.VK_ENTER) {
                if (playerName.length() > 2) {
                    phase = PHASES.SELECT_TRUMPH;
                    note1 = "";
                } else {
                    note1 = "Dein Name muss mindestens 3 Zeichen lang sein!";
                }

            } else if (f.validKeyCode(keyCode) && playerName.length() < 20) {
                playerName += keyChar;
            }
            if (playerName.length() > 2) {
                note1 = "";
            }
        }
    }

    public void update() {
        switch (phase){
            case SELECT_NAME:
                break;
            case SELECT_TRUMPH:
                if(!playerStarts)
                    f.reactionTime(3);
                break;
            case PLAYING:

                switch (playingPhase){
                    case FIRST_CARD:
                        if(playerStarts){
                            getMove();
                        }else{
                            f.reactionTime(1);
                            botSelectsCard();
                        }
                        playingPhase = PLAINGPHASES.SECOND_CARD;
                        break;
                    case SECOND_CARD:
                        if(playerStarts){
                            f.reactionTime(2);
                            botSelectsCard();
                        }else{
                            getMove();
                        }
                        playingPhase = PLAINGPHASES.EVALUATE;
                        break;
                    case EVALUATE:
                        f.reactionTime(2);
                        OUTCOME oc = evaluate(playersSelectedCard,botsSelectedCard);
                        System.out.println(oc.toString() + " won");
                        setNextRound(oc);
                        break;
                }

                break;
            case RESULT:
                if(winner != null) {
                    if (winner.equals(OUTCOME.PLAYER) && !statLineAdded && playerScore != 0) {
                        statLine = new String[]{f.getTime(), playerName, "" + playerScore};
                        scoreboardManager.addScore(statLine);
                        statLineAdded = true;
                    }
                }
                HowToContinue();

        }
    }

    public void draw(Graphics g) {
        switch (phase) {
            case SELECT_NAME:
                //drawScoreboard(g);
                drawSelectName(g);
                break;
            case SELECT_TRUMPH:
                //drawScoreboard(g);
                drawSelectTrumph(g);
                break;
            case PLAYING:
                //drawScoreboard(g);
                drawPlaying(g);
                break;
            case RESULT:
                drawResult(g);
                break;
            case HIGHSCORELIST:
                drawHighscorelist(g);
                break;

        }
        drawAtAllTimes(g);
    }

    public void drawAtAllTimes(Graphics g){
        //X BOX
        for(ClickableBox box : allTimeBoxen){
            g.setColor(box.color);
            g.fillRect(box.x1,box.y1,box.b,box.h);
            g.setColor(Color.BLACK);
            g.drawRect(box.x1,box.y1,box.b,box.h);
            g.drawLine(box.x1,box.y1,box.x2-2,box.y2-2);
            g.drawLine(box.x1,box.y2-2,box.x2-2, box.y1);
        }


    }

    public void drawScoreboard(Graphics g) {
        g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        g.drawString(playerName, 50, 50);
        g.drawString(trumph, 50, 70);
        g.drawString(phase.toString(), 50, 90);
        if(phase == PHASES.PLAYING){
            g.drawString("Round : "+round,50,110);
        }
        g.drawString(difficulty.toString(),50,130);
    }

    public void drawSelectTrumph(Graphics g) {
        if (playerStarts) {
            //drawing the decks
            for (Card card : botDeck.getHiddenCards()) {
                g.drawImage(rueckseite, card.getX() - 10, card.getY() - 10, null);
            }
            for (Card card : playerDeck.getHiddenCards()) {
                g.drawImage(rueckseite, card.getX() - 10, card.getY() - 10, null);
            }
            for (int i = 4; i < 8; i++) {
                Card card = playerDeck.getCards()[i];
                g.drawImage(card.getImage(), card.getX(), card.getY(), null);
            }
        } else {
            Card[] cardSelection = new Card[4];
            //drawing the decks
            for (Card card : botDeck.getHiddenCards()) {
                g.drawImage(rueckseite, card.getX() - 10, card.getY() - 10, null);
            }
            for (Card card : playerDeck.getHiddenCards()) {
                g.drawImage(rueckseite, card.getX() - 10, card.getY() - 10, null);
            }
            for (int i = 0; i < 4; i++) {
                Card card = botDeck.getCards()[i];
                cardSelection[i] = card;
                g.drawImage(card.getImage(), card.getX(), card.getY(), null);
            }
            botSelectsTrumph();


        }
    }

    public void drawSelectName(Graphics g) {
        int x = 360;
        int y = 200;
        int b = 680;
        int h = 80;

        g.setColor(Color.GRAY);
        g.fillRect(x, y, b, h);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString(playerName, x + 10, y + 55);
        g.setFont(new Font("Monospaced", Font.ITALIC, 25));
        g.drawString(note1, x - 4, y + 110);
        g.drawString("Gib deinen Spielernamen ein und drücke \"Enter\"!", x - 4, y - 10);


        for(ClickableBox box : difficultyBoxen){
            g.setColor(box.color);
            g.fillRect(box.x1,box.y1,box.b,box.h);
            g.setFont(new Font("Times New Roman",Font.ITALIC,30));
            g.setColor(Color.BLACK);
            g.drawString(box.text,box.x1+10, box.y1+40);
        }
        ClickableBox box =null;
        switch (difficulty){
            case EASY:
                box = difficultyBoxen.get(0);
                break;
            case MEDIUM:
                box = difficultyBoxen.get(1);
                break;
            case HARD:
                box = difficultyBoxen.get(2);
                break;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(box.x1,box.y1,box.b,box.h);

        for(ClickableBox box2 : selectNamesBoxen){
            g.setColor(box2.color);
            g.fillRect(box2.x1,box2.y1,box2.b,box2.h);
            g.setFont(new Font("Times New Roman",Font.ITALIC,30));
            g.setColor(Color.BLACK);
            g.drawString(box2.text,box2.x1+10, box2.y1+40);
        }

    }


    public void drawPlaying(Graphics g) {

        //drawing the decks
        for (Card card : botDeck.getHiddenCards()) {
            if(card != null) {
                g.drawImage(rueckseite, card.getX() - 10, card.getY() - 10, null);
            }
        }
        for (Card card : botDeck.getCards()) {
            if (card != null) {
                g.drawImage(card.getImage(), card.getX(), card.getY(), null);
            }
        }
        for (Card card : playerDeck.getHiddenCards()) {
            if(card != null) {
                g.drawImage(rueckseite, card.getX() - 10, card.getY() - 10, null);
            }
        }
        for (Card card : playerDeck.getCards()) {
            if (card != null) {
                g.drawImage(card.getImage(), card.getX(), card.getY(), null);
            }
        }
        if (playersSelectedCard != null) {
            g.drawString("Your Card -> ", 485, 420);
            g.drawImage(playersSelectedCard.getImage(), 600, 370, null);
        }
        if (botsSelectedCard != null) {
            g.drawString("<- Enemies Card", 850, 420);
            g.drawImage(botsSelectedCard.getImage(), 780, 370, null);
        }
        //GEWONNENE Karten anzeigen
        for(int i = 0; i < playersWonCards.size()/2; i++){
            g.drawImage(rueckseite,40+i*10,500,null);
        }
        for(int i = 0; i < botsWonCards.size()/2; i++){
            g.drawImage(rueckseite,1250+i*10,100,null);
        }
        //Punkte anzeigen
        if(playerPointsShown && !playersWonCards.isEmpty()){
            g.drawString("Punkte : "+f.getPoints(playersWonCards),40,610);
        }
        if(botPointsShown && !botsWonCards.isEmpty()){
            g.drawString("Punkte : "+f.getPoints(botsWonCards),1250,210);
        }
        //Trumphanzeigen
        int ty = 680;
        if(gameStats.getStarter().equals(OUTCOME.BOT)){
            ty = 150;
        }
        g.setFont(new Font("Times New Roman",Font.BOLD,30));
        g.drawString('\"'+trumph+'\"',1070,ty);

    }

    public void drawResult(Graphics g){
        int playerPoints = f.getPoints(playersWonCards);
        int botPoints = f.getPoints(botsWonCards);

        if(gameStats.getStarter().equals(OUTCOME.PLAYER)){
            winner = playerPoints > 60 ? OUTCOME.PLAYER : OUTCOME.BOT;
        }else{
            winner = botPoints > 60 ? OUTCOME.BOT : OUTCOME.PLAYER;
        }


        String s1 = winner.equals(OUTCOME.PLAYER) ? "  YOU WIN" : "YOU LOOSE";
        g.setFont(new Font("Ariel",Font.BOLD,100));
        g.drawString(s1,400,120);


        int h = 500;
        int b = 400;
        int y = 150;
        int x = 250;
        g.setColor(playerColor);
        g.fillRect(x,y,b,h);
        g.setColor(Color.black);
        g.fillRect(1400-b-x,y,b,h);

        g.setFont(new Font("Times New Roman",Font.PLAIN,30));
        g.drawString(playerName,x+20,y+40);
        g.drawLine(x,y+55,x+b,y+55);
        g.drawString("Punkte : "+playerPoints,x+ 20, y+90);
        g.drawString("Stiche : "+playersWonCards.size()/2,x+ 20, y+130);
        g.drawString("Buben : ",x+20,y+170);
        int i = 0;
        for(Card card : gameStats.getPlayerJacks()){
            g.drawImage(card.getImage(),x+20+ i*70,y+210,null);
            i++;
        }
        //g.drawString("Spite: " +gameStats.getSpitze(),x+20,y+350);


        g.setColor(Color.white);
        g.drawString(botName,1400-x-b+20,y+40);
        g.drawLine(1400-x-b,y+55,1400-x,y+55);
        g.drawString("Punkte : "+botPoints,1400-x-b+20,y+90);
        g.drawString("Stiche : "+botsWonCards.size()/2,1400-x-b+20, y+130);
        g.drawString("Buben : ",1400-x-b+20,y+170);
        i = 0;
        for(Card card : gameStats.getBotJacks()){
            g.drawImage(card.getImage(),1400-x-b+20+ i*70,y+210,null);
            i++;
        }


        //ERGEBNIS


        String s2,s3 = null,s4 = null,s5 = null;
        int spiel = gameStats.getSpitze()+1;
        s2 = gameStats.getMit() + gameStats.getSpitze() +" spielt "+spiel;
        if(winner.equals(OUTCOME.PLAYER)){
            if(botPoints <30){
                spiel++;
                s3 = "Schneider "+spiel;
                if(botsWonCards.isEmpty()){
                    spiel++;
                    s4= "Schwarz "+spiel;
                }
            }
        }else{
            if(playerPoints < 30){
                spiel++;
                s3 = "Schneider "+spiel;
                if(playersWonCards.isEmpty()){
                    spiel++;
                    s4 = "Schwarz "+spiel;
                }
            }
        }
        if(kontra){
            spiel *= 2;
            s5 = "Kontra (x2) =>"+spiel;
        }



        g.setFont(new Font("Times New Roman",Font.PLAIN,20));
        g.setColor(Color.BLACK);

        if(winner.equals(OUTCOME.BOT)){
            g.setColor(Color.white);
            x += 500;
        }


        int y2 = y+350;
        g.drawString(s2,x+20,y2);
        y2 += 30;
        if(s3 != null) {
            g.drawString(s3, x + 20, y2);
            y2+=30;
        }
        if(s4 != null){
            g.drawString(s4,x+20,y2);
            y2+=30;
        }
        if(s5!= null){
            g.drawString(s5,x+20,y2);
            y2 += 30;
        }
        playerScore = spiel*f.getSpielwert(trumph);
        String s6 = spiel +" x "+f.getSpielwert(trumph) + " = "+playerScore;
        g.drawString(s6,x+20,y2);
        y2 += 30;

        g.setFont(new Font("Times New Roman",Font.BOLD,40));
        g.drawString(""+playerScore,x+300,y+470);


        //ClickableBoxen drawn

        for(ClickableBox box : resultBoxen){
            g.setColor(box.color);
            g.fillRect(box.x1,box.y1,box.b,box.h);
            g.setFont(new Font("Times New Roman",Font.ITALIC,30));
            g.setColor(Color.BLACK);
            g.drawString(box.text,box.x1+10, box.y1+40);
        }




    }
    
    public void drawHighscorelist(Graphics g){
        String[][] scoreBoard = scoreboardManager.getScoreBoard();
        int h = scoreBoard.length < 10 ? scoreBoard.length : 10;
        for(int i = 0; i < h; i++){
            switch (i){
                case 0:
                    g.setColor(Color.decode("#FFD700"));
                    break;
                case 1:
                    g.setColor(Color.decode("#C0C0C0"));
                    break;
                case 2:
                    g.setColor(Color.decode("#CD7F32"));
                    break;
                default:
                    g.setColor(Color.CYAN);
                    break;
            }
            g.fillRect(250,30+i*60,900,50);
            g.setColor(Color.BLACK);
            g.drawRect(250,30+i*60,900,50);
            g.setFont(new Font("Times New Roman", Font.BOLD,30));
            int x = 260;
            for(int k = 0; k < 3 ; k++){
                g.drawString(scoreBoard[i][k],x,65+i*60);
                x += 300+ k*210;
            }

        }

        for(ClickableBox box : highscoreBoxen){
            if(!box.text.equals("Zurück zum Spiel") || zurückZumSpiel) {
                g.setColor(box.color);
                g.fillRect(box.x1, box.y1, box.b, box.h);
                g.setFont(new Font("Times New Roman", Font.ITALIC, 30));
                g.setColor(Color.BLACK);
                g.drawString(box.text, box.x1 + 10, box.y1 + 40);
            }
        }
    }


    public void botSelectsTrumph() {

        Card[] cards = new Card[4];
        for (int i = 0; i < 4; i++) {
            cards[i] = botDeck.getCards()[i];
        }

        int kreuz = 0;
        int peak = 0;
        int herz = 0;
        int karo = 0;
        int jack = 0;

        //final String[] ranks = {"7", "8", "9", "10", "Jack", "Queen", "King","Ace"};
        //final String[] suits = {"Kreuz", "Peak", "Herz", "Karo"};


        for (Card card : cards) {
            int faktor = 0;
            switch (card.getRank()) {
                case "7":
                    faktor = 1;
                    break;
                case "8":
                    faktor = 2;
                    break;
                case "9":
                    faktor = 3;
                    break;
                case "Queen":
                    faktor = 4;
                    break;
                case "King":
                    faktor = 5;
                    break;
                case "10":
                    faktor = 6;
                    break;
                case "Ace":
                    faktor = 7;
                    break;
                case "Jack":
                    jack += 1;
                    break;
                default:
                    System.out.println("BotSelectsTrumph Error -> Rank nicht erkannt ");
                    break;
            }

            switch (card.getSuit()) {
                case "Kreuz":
                    kreuz += faktor;
                    break;
                case "Peak":
                    peak += faktor;
                    break;
                case "Herz":
                    herz += faktor;
                    break;
                case "Karo":
                    karo += faktor;
                    break;
                default:
                    System.out.println("BotselectsTrumph Error -> Suit nicht erkannt ");
                    break;
            }
        }

        if (jack > 2) {
            trumph = "Grand";
        } else if (kreuz >= peak && kreuz >= herz && kreuz >= karo) {
            trumph = "Kreuz";
        } else if (peak >= herz && peak >= karo) {
            trumph = "Peak";
        } else if (herz >= karo) {
            trumph = "Herz";
        } else {
            trumph = "Karo";
        }

        phase = PHASES.PLAYING;
        System.out.println("PLAYING STARTS");
    }

    public void botSelectsCard() {

        boolean smartMove = false;
        switch (difficulty){
            case DIFFICULTY.EASY -> smartMove = false;
            case DIFFICULTY.MEDIUM -> smartMove = r.nextBoolean();
            case DIFFICULTY.HARD -> smartMove = true;
        }

        if(smartMove){
            if(playingPhase.equals(PLAINGPHASES.FIRST_CARD)){
                for(Card card: botDeck.getCards()){
                    if(card != null ) {
                        if (card.getRank().equals("Ace") && !card.getSuit().equals(trumph) && f.checkForSuit(playerDeck.getCards(), card.getSuit())) {
                            botsSelectedCard = botDeck.takeCard(card);
                            return;
                        }
                        if (card.getRank().equals("10") && !card.getSuit().equals(trumph) && f.checkForSuit(playerDeck.getCards(), card.getSuit()) && !f.checkForCard(playerDeck.getCards(), card.getSuit(), "Ace")) {
                            botsSelectedCard = botDeck.takeCard(card);
                            return;
                        }
                        if (card.getRank().equals("Ace") && !f.chechForRank(playerDeck.getCards(), "Jack")) {
                            botsSelectedCard = botDeck.takeCard(card);
                            return;
                        }
                        if (card.getRank().equals("10") && !f.chechForRank(playerDeck.getCards(), "Jack") && !f.checkForCard(playerDeck.getCards(), trumph, "Ace")) {
                            botsSelectedCard = botDeck.takeCard(card);
                            return;
                        }
                    }
                }
                for(Card card : botDeck.getCards()){
                    if(card != null){
                        if (card.getRank().equals("King") && !card.getSuit().equals(trumph) && f.checkForSuit(playerDeck.getCards(), card.getSuit()) && !f.checkForCard(playerDeck.getCards(), card.getSuit(), "Ace") && !f.checkForCard(playerDeck.getCards(), card.getSuit(), "10")) {
                            botsSelectedCard = botDeck.takeCard(card);
                            return;
                        }
                        if(card.getSuit().equals(trumph) && !f.checkForCard(playerDeck.getCards(),trumph,"Ace") && !f.checkForCard(playerDeck.getCards(),trumph,"10")){
                            botsSelectedCard = botDeck.takeCard(card);
                            return;
                        }
                        if((card.getSuit().equals(trumph) || card.getRank().equals("Jack")) && card.getPostion(trumph,trumph) < f.besteAusmDeck(playerDeck.getCards(),trumph)){
                            botsSelectedCard = botDeck.takeCard(card);
                            return;
                        }

                    }
                }
                for(Card card: botDeck.getCards()){
                    if(card != null){
                        if(!f.checkForSuit(playerDeck.getCards(),card.getSuit())){
                            botsSelectedCard = botDeck.takeCard(card);
                            return;
                        }
                    }
                }

                //REST OPTIONEN  vielleicht noch optiemieren?
                for(Card card: botDeck.getCards()){
                    if(card != null){
                        botsSelectedCard = botDeck.takeCard(card);
                        return;
                    }
                }

            } else if (playingPhase.equals(PLAINGPHASES.SECOND_CARD)) {
                String farbe = playersSelectedCard.getSuit();
                for(Card card : botDeck.getCards()){
                    if(card != null && isCardValidPick(card,OUTCOME.PLAYER)){
                        //ALGORITMUS FÜR ZWEITE KARTE
                        if(card.getPostion(trumph,farbe) < playersSelectedCard.getPostion(trumph,farbe) &&  (card.getRank().equals("10") || card.getRank().equals("Ace") ) ){
                            botsSelectedCard = botDeck.takeCard(card);
                            return;
                        }
                    }
                }
                for(Card card : botDeck.getCards()){
                    if(card != null && isCardValidPick(card,OUTCOME.PLAYER)){
                        //ALGORITMUS FÜR ZWEITE KARTE
                        if(card.getPostion(trumph,farbe) < playersSelectedCard.getPostion(trumph,farbe)){
                            botsSelectedCard = botDeck.takeCard(card);
                            return;
                        }
                    }
                }
                Card[] deck = botDeck.getCards();
                Card selectedCard = null;
                while (selectedCard == null) {
                    int num;
                    do {
                        num = r.nextInt(8);
                    } while (deck[num] == null || !isCardValidPick(deck[num], OUTCOME.BOT));

                    selectedCard = deck[num];
                }

                botsSelectedCard = botDeck.takeCard(selectedCard);
                System.out.println("Bot Selected: " + selectedCard.getName());

            }
        }else {
            Card[] deck = botDeck.getCards();
            Card selectedCard = null;
            while (selectedCard == null) {
                int num;
                do {
                    num = r.nextInt(8);
                } while (deck[num] == null || !isCardValidPick(deck[num], OUTCOME.BOT));

                selectedCard = deck[num];
            }

            botsSelectedCard = botDeck.takeCard(selectedCard);
            System.out.println("Bot Selected: " + selectedCard.getName());
        }
    }

    public void getMove(){
        playerMoveRequested = true;
        while(playerMoveRequested){
            f.sleeper(1);
        }
    }




    public OUTCOME evaluate(Card playerCard, Card botCard) {
        Card firstCard, secondCard;

        int firstPos,secondPos;

        firstCard = playerStarts ? playerCard : botCard;
        secondCard = playerStarts ? botCard : playerCard;

        String farbe = firstCard.getRank().equals("Jack") ? trumph : firstCard.getSuit();

        int playersPos, botsPos;



        playersPos = playerCard.getPostion(trumph,farbe);
        botsPos = botCard.getPostion(trumph,farbe);

        System.out.println("P : "+playersPos+ " | B : "+botsPos);

        if(playersPos < botsPos){
            return OUTCOME.PLAYER;
        } else if (botsPos < playersPos) {
            return OUTCOME.BOT;
        }else{
            System.out.println("ERROR -> evaluate");
            return null;
        }


    }

    public void setNextRound(OUTCOME oc){
        if(oc == OUTCOME.PLAYER){
            playerStarts = true;
            playersWonCards.push(playersSelectedCard);
            playersWonCards.push(botsSelectedCard);

        }else{
            playerStarts = false;
            botsWonCards.push(playersSelectedCard);
            botsWonCards.push(botsSelectedCard);
        }

        playersSelectedCard = null;
        botsSelectedCard = null;
        updateDecks();
        playingPhase = PLAINGPHASES.FIRST_CARD;
        round++;
        if(round > 16){
            phase = PHASES.RESULT;
        }
    }

    public void updateDecks(){
        for(int i = 0; i < 8; i++){
            if(playerDeck.getCard(i) == null && playerDeck.getHiddenCard(i) != null){
                playerDeck.moveCard(i);
            }
            if(botDeck.getCard(i) == null && botDeck.getHiddenCard(i) != null){
                botDeck.moveCard(i);
            }
        }
    }

    public void HowToContinue(){

    }

    public boolean isCardValidPick(Card card, OUTCOME active){
        if(playingPhase.equals(PLAINGPHASES.FIRST_CARD)){
            return true;
        }
        Card firstCard = playersSelectedCard != null ? playersSelectedCard : botsSelectedCard;
        if(trumph.equals("Grand") && firstCard.getRank().equals("Jack")){
            return card.getRank().equals("Jack");
        }
        if(firstCard.getRank().equals("Jack")){
            if(card.getRank().equals("Jack") || card.getSuit().equals(trumph)){
                return true;
            }else{
                return false;
            }
        }
        if(firstCard.getSuit().equals(trumph)){
            if(card.getRank().equals("Jack") || card.getSuit().equals(trumph)){
                return true;
            }
        }
        if(firstCard.getSuit().equals(card.getSuit()) && !card.getRank().equals("Jack")){
            return true;
        }
        boolean vorhanden;
        if(active.equals(OUTCOME.PLAYER)){
            vorhanden = f.checkForSuit(playerDeck.getCards(),firstCard.getSuit());
        }else{
            vorhanden = f.checkForSuit(botDeck.getCards(),firstCard.getSuit());
        }
        return !vorhanden;
    }

    public void newGame(){
        phase = PHASES.SELECT_NAME;
        gameStats = new GameStats();
        cardManager =new CardManager();
        playerName = "";
        trumph = "";
        note1 = "";
        kontra = false;
        playerMoveRequested = false;
        playingPhase = PLAINGPHASES.FIRST_CARD;
        round = 1;
        playerDeck = new Deck(cardManager.getCardStack1(),"player");
        botDeck = new Deck(cardManager.getCardStack2(),"bot");
        giveCords(playerDeck);
        giveCords(botDeck);
        gameStats.setJacks(playerDeck,botDeck);
        playerPoints = 0;
        botPoints = 0;
        playerStarts = false;
        gameStats.setStarter(playerStarts ? OUTCOME.PLAYER : OUTCOME.BOT);
        statLineAdded =false;
        playersWonCards = new Stack<Card>();
        botsWonCards = new Stack<Card>();

    }

}





