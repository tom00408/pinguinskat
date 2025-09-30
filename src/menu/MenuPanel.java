package menu;


import skat.Skat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPanel extends JPanel {

    final int screenWidth = 1792;
    final int screenHeight = 1024;

    BufferedImage background, skatIcon;

    ArrayList<Game> games = new ArrayList<>();

    public MenuPanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        loadImages();
        loadGames();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                getMouseClick(x,y);
            }
        });

        repaint();
    }

    void loadImages(){
        try{
            background = ImageIO.read(getClass().getResourceAsStream("/Bib.jpeg"));
            skatIcon = ImageIO.read(getClass().getResourceAsStream("/SkatIcon.jpg"));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    void loadGames(){
        Game skat = new Game("Skat");
        skat.setPos(830,370,200,130);
        skat.setImg(skatIcon);
        skat.setClickable(true);
        games.add(skat);
    }

    public void  getMouseClick(int x, int y){
        for(Game game : games){
            if(game.clicked(x,y) && game.isClickable()){
                switch (game.name){
                    case "Skat":
                        Skat.playSkat();
                }
            }
        }


        repaint();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(background,0,0,null);

        for(Game game: games){
            if(game.isClickable()){
                game.draw(g);
            }
        }
    }


}
